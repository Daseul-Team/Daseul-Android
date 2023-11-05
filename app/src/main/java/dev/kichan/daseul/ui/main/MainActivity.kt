package dev.kichan.daseul.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.JsonArray
import dev.kichan.daseul.R
import dev.kichan.daseul.base.BaseActivity
import dev.kichan.daseul.databinding.ActivityMainBinding
import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.data.test.data_infoGroup
import dev.kichan.daseul.model.data.test.getReturnUserInfo
import dev.kichan.daseul.model.data.test.getuserInfo
import dev.kichan.daseul.model.service.Group
import dev.kichan.daseul.ui.main.group.GroupListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){
    private val navController : NavController by lazy { findNavController(R.id.fragment_main) }
    private val retrofit = RetrofitClient.getRetrofit()
    private var Token = ""
    val groupDataList = mutableListOf<data_infoGroup>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 사용자 위치 권한 허용
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val permissionCheck =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

        }
    }

    override fun initView() = binding.run {
        bnvMain.setupWithNavController(navController)
        send_list()
    }
    fun showGroupList(){
        Log.d("fortest","리사이클러뷰 생성 직전 리스트 값 = "+ groupDataList)
            val service = retrofit.create(Group::class.java)
            Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")

        val deferredJobs = groupDataList.map { item ->
            processGroupDataList(item)
        }

        runBlocking {
            // 모든 비동기 작업이 완료될 때까지 기다림
            deferredJobs.awaitAll()
        }


        Log.d("fortest","최종 수정된 이름 값 = "+groupDataList)
        val bottomSheetFragment = GroupListFragment(groupDataList,Token)
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
    fun processGroupDataList(item : data_infoGroup) = CoroutineScope(Dispatchers.IO).async {
        val service = retrofit.create(Group::class.java)
        val token = "Bearer " + getSharedPreferences("Group_Info", 0).getString("token", "")

        val updatedWhoList = mutableListOf<String>() // 변경된 who 목록을 저장할 리스트

        for (index in item.who) {
            var requestBody = getuserInfo(item.group_id, index)
            val call: Call<getReturnUserInfo> = service.getuserinfo(token, requestBody)
            val response = call.execute()

            if (response.isSuccessful) {
                val newName = response.body()?.name ?: ""
                updatedWhoList.add(newName)
            } else {
                // 요청이 실패한 경우
                val body = response.errorBody()?.string() ?: ""
                Log.d("Group_test", "error - body : $body")
                updatedWhoList.add(index) // 실패 시 원래 값 유지
            }
        }

        Log.d("fortest", "수정된 who값 = " + updatedWhoList)
        groupDataList[groupDataList.indexOf(item)].who = updatedWhoList
    }

    private fun getKakaoKeyHash() {
        try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA").apply {
                    update(signature.toByteArray())
                }
                val HASH_CODE = String(Base64.encode(md.digest(), 0))

                Log.d("kakao sha", "HASH_CODE -> $HASH_CODE")
            }
        } catch (e: Exception) {
            Log.d("kakao sha", "Exception -> $e")
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
    fun send_list(){
        var itemList = mutableListOf<String>()
            val service = retrofit.create(Group::class.java)
            Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
            Log.d("fortest","받은 결과 ="+Token)
        val call: Call<JsonArray> = service.getListGroupId(Token)
        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful) {
                    val jsonArray: JsonArray? = response.body()
                    // 결과를 저장할 리스트

                    for (element in jsonArray!!) {
                        if (element.isJsonPrimitive) {
                            val item = element.asString
                            itemList.add(item)
                        }
                    }

                    // itemList에 데이터가 동적으로 저장됨
                    for (item in itemList) {
                        Log.d("fortest", "Item $item")
                    }
                    Log.d("fortest","그룹리스트 받아오기 완료")
                    returnGroupInfo(itemList)


                } else {
                    // 요청이 실패한 경우
                    val body = response.errorBody()!!.string()
                    Log.d("Group_test", "error - body : $body")
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리
            }
        })
    }
    fun returnGroupInfo(itemList : List<String>){
        val service = retrofit.create(Group::class.java)
        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        Log.d("fortest","itemList 데이터 = "+itemList)
        itemList.forEachIndexed { index, item ->
            coroutineScope.launch {
                val call: Call<data_infoGroup> = service.getGroupInfo(Token, item)
                val response = call.execute()

                if (response.isSuccessful) {
                    val dataInfoGroup = response.body() // 이 부분은 data_infoGroup 형식의 응답을 받는다고 가정합니다.

                    dataInfoGroup?.let { itemData ->
                        // itemData에서 원하는 데이터 추출
                        val name = itemData.name
                        val image = itemData.image
                        val participants = itemData.who

                        // 새로운 data_infoGroup 객체 생성
                        val groupData = data_infoGroup(name, image, participants,item)
                        Log.d("fortest", "그룹정보결과 = $groupData")
                        // 리스트에 추가
                        groupDataList.add(groupData)
                    }
                } else {
                    val body = response.errorBody()!!.string()
                    Log.d("Group_test", "error - body : $body")
                }

                if (index == itemList.size - 1) {
                }
            }
        }
    }
}