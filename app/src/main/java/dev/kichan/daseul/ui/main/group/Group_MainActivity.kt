package dev.kichan.daseul.ui.main.group

import android.content.Context
import android.graphics.Paint.Join
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import com.google.gson.Gson
import com.google.gson.JsonArray
import dev.kichan.daseul.R
import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.data.test.data_hash_img
import dev.kichan.daseul.model.data.test.data_infoGroup
import dev.kichan.daseul.model.data.test.data_invite
import dev.kichan.daseul.model.data.test.data_invite_body
import dev.kichan.daseul.model.data.test.joinGroup
import dev.kichan.daseul.model.data.test.make_group
import dev.kichan.daseul.model.service.Group
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.time.LocalDate


class Group_MainActivity : AppCompatActivity() {
    private var User_Info = ""
    private var User_img = ""
    private var Group_img = ""
    private var Group_id = ""
    private val retrofit = RetrofitClient.getRetrofit()

    fun return_imgkey(name_value: String) {
        User_Info = name_value
        val Token = "Bearer " + getSharedPreferences("Group_Info", 0).getString("token", "")
        Log.d("fortest", "이미지 변환에서 확인하는 유저값" + User_Info)

        val imageurl = getFilePathFromUri(User_img,this)
        Log.d("fortest", "이미지 path 받은 값 = " + imageurl)

        val imageFile = File(imageurl) // 이미지 파일 경로로부터 File 객체 생성

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        val apiService = retrofit.create(Group::class.java)

        val call = apiService.uploadImage(Token, body)
        call.enqueue(object : Callback<data_hash_img> {
            override fun onResponse(call: Call<data_hash_img>, response: Response<data_hash_img>) {
                if (response.isSuccessful) {
                    Log.d("Group_test", "isSuccessful() : " + response.code())
                    Log.d("Group_test", "받은 리턴값 :" + response.body()?.hash)
                    Group_img = response.body()?.hash.toString()
                    Retrofit_MakeGroup()
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("Group_test", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<data_hash_img>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리
                Log.d("Group_test", "API FAIL: ${call}")
                Log.d("Group_test", "API FAIL: ${t.message}")
            }
        })
    }
    fun getFilePathFromUri(uriString: String, context: Context): String? {
        val uri = Uri.parse(uriString)
        var filePath: String? = null
        if (uri.scheme == "content") {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    filePath = it.getString(columnIndex)
                }
            }
        } else if (uri.scheme == "file") {
            filePath = uri.path
        }
        return filePath
    }
    fun Retrofit_MakeGroup (){
        val service = retrofit.create(Group::class.java)
        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        Log.d("fortest_name","만들기 직전 받은 이름 = "+User_Info)
        val requestBody = make_group(User_Info,Group_img)
        val call = service.makeGroup(Token,requestBody)
        Log.d("fortest","받은 결과 ="+Token)
        call.enqueue(object : Callback<data_create> {
            override fun onFailure(call: Call<data_create>, t: Throwable) {
                Log.d("Group_test", "API FAIL: ${call}")
            }
            override fun onResponse(call: Call<data_create>, response: Response<data_create>) {
                if (response.isSuccessful) {
                    Log.d("Group_test", "isSuccessful() : " + response.code())
                    Log.d("Group_test", "받은 리턴값 :" + response.body()+"id = "+response.body()?.id)
                    Group_id = response.body()?.id.toString()
                    convertInvite()
                } else {
                    try {
                        val body = response.errorBody()!!.string()
                        Log.d("Group_test", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })

    }
   fun convertInvite() {
       val service = retrofit.create(Group::class.java)
       val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
       Log.d("fortest","받은 결과 ="+Token)
       val gson = Gson()
       val new_data_invite = data_invite_body(Group_id)
       val dataInviteBody = RequestBody.create("application/json".toMediaTypeOrNull(), gson.toJson(new_data_invite))

       val call = service.convertInvite(Token, dataInviteBody)
       call.enqueue(object : Callback<data_invite> {
           override fun onResponse(call: Call<data_invite>, response: Response<data_invite>) {
               if (response.isSuccessful) {
                   // 요청이 성공했을 때의 처리
                   Log.d("Group_test", "isSuccessful() : " + response.code())
                   Log.d("Group_test", "받은 리턴값 :" + response.body())
                   val bundle = Bundle()
                   bundle.putString("key_invite", response.body()?.invite_id.toString())
                   val groupCompleteFragment = GroupCompleteFragment()
                   groupCompleteFragment.arguments = bundle
                   supportFragmentManager.beginTransaction()
                       .replace(R.id.group_frame, groupCompleteFragment)
                       .commit()
               } else {
                   // 요청이 실패했을 때의 처리
                   val body = response.errorBody()!!.string()
                   Log.d("Group_test", "error - body : $body")
               }
           }

           override fun onFailure(call: Call<data_invite>, t: Throwable) {
               // 네트워크 오류 등으로 요청이 실패했을 때의 처리
           }
       })
   }
    fun receiveDataFromFragment(data: Bundle) {
        User_Info = data.getString("key_name").toString()
        User_img = data.getString("key_img").toString()
    }
    fun JoinGroup(data: String){
        val service = retrofit.create(Group::class.java)
        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        var joingroup = joinGroup(data)
        val call = service.joinGroup(Token,joingroup)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Group_test", "isSuccessful() : " + response.code())
                    Log.d("Group_test", "받은 리턴값 :" + response.body())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.group_frame, InviteCompleteFragment())
                        .commit()
                } else {
                    Log.d("Group_test", "error - body : ${response.errorBody()}")
                    Log.d("Group_test", "error - body : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 또는 예외 처리
                Log.e("NetworkFailure", "Call failed: ${t.message}")
                t.printStackTrace()
            }
        })



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_group_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.group_frame, Group_SelectFragment())
            .commit()

    }
}