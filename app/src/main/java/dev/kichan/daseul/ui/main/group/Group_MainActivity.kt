package dev.kichan.daseul.ui.main.group

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dev.kichan.daseul.R
import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.data.test.data_hash_img
import dev.kichan.daseul.model.data.test.data_invite
import dev.kichan.daseul.model.data.test.data_invite_body
import dev.kichan.daseul.model.service.Group
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class Group_MainActivity : AppCompatActivity(){
    private lateinit var User_Info : String
    private lateinit var User_img : String
    private lateinit var Group_img : String
    private lateinit var Group_id : String
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://api.daseul.deltalab.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun return_imgkey(){

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        Log.d("fortest", "이미지 path 받은 값 = "+User_img)

        val imageFile = getFilePathFromUri(User_img,this)
        Log.d("fortest","변환된 이미지path = "+imageFile)

        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
        val body = MultipartBody.Part.createFormData("file", "file", requestFile)

        val apiService = retrofit.create(Group::class.java)

        val call = apiService.uploadImage(Token,body)
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

        val retrofit = Retrofit.Builder().baseUrl("https://api.daseul.deltalab.dev/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(Group::class.java)
        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        Log.d("fortest","받은 결과 ="+Token)
        Log.d("fortest","받은 이름 = "+User_Info)
        service.makeGroup(Token, User_Info, Group_img)?.enqueue(object : Callback<data_create> {
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
       val loggingInterceptor = HttpLoggingInterceptor()
       loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

       val okHttpClient = OkHttpClient.Builder()
           .addInterceptor(loggingInterceptor)
           .build()

       val retrofit = Retrofit.Builder().baseUrl("https://api.daseul.deltalab.dev/")
           .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
       val service = retrofit.create(Group::class.java)
       val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
       Log.d("fortest","받은 결과 ="+Token)
       Log.d("fortest","받은 이름 = "+User_Info)
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
//        retrofit = Retrofit.Builder()
//            .baseUrl("https://api.daseul.deltalab.dev/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val apiService = retrofit.create(Group::class.java)
//
//        val accessToken = "Bearer " + getSharedPreferences("Group_Info", 0).getString("token", "")
//        val requestBody = data_invite_body("your_group_id_here") // 필드에 적절한 값 할당
//
//        try {
//            val response = apiService.convertInvite(accessToken, requestBody)
//
//            try {
//                val call = apiService.convertInvite(accessToken, requestBody)
//                val response = call.execute() // 동기적으로 호출
//
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    // 응답 데이터 처리
//                } else {
//                    // 오류 응답 처리
//                    val errorBody = response.errorBody()?.string()
//                    Log.d("Group_test", "Error response: $errorBody")
//                }
//            } catch (e: Exception) {
//                // 네트워크 오류 또는 예외 처리
//                Log.e("Group_test", "Network error: ${e.message}", e)
//            }
//        }catch (e: Exception) {
//            // 예외 처리
//        }
//    }
    fun receiveDataFromFragment(data: Bundle) {
        User_Info = data.getString("key_name").toString()
        User_img = data.getString("key_img").toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_group_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.group_frame, Group_SelectFragment())
            .commit()

    }

}