package dev.kichan.daseul.ui.main.nav.group

import android.content.SharedPreferences
import android.media.session.MediaSession.Token
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.kichan.daseul.R
import dev.kichan.daseul.model.data.test.CommonRes
import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.service.Group
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dev.kichan.daseul.model.data.test.kakaoOauthres
import java.io.IOException

class Group_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_main)
        val retrofit = Retrofit.Builder().baseUrl("https://api.daseul.deltalab.dev/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(Group::class.java);
        val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
        Log.d("fortest","받은 결과 ="+Token)

        service.getUserPage(Token,"test")?.enqueue(object : Callback<data_create> {
            override fun onFailure(call: Call<data_create>, t: Throwable) {
                Log.d("Group_test", "API FAIL: ${call}")
            }
            override fun onResponse(
                call: Call<data_create>,
                response: Response<data_create>
            ) {
                if (response.isSuccessful()) {
                    Log.d("Group_test", "isSuccessful() : " + response.code());
                }
                else
                {
                    try { val body = response.errorBody()!!.string()
                        Log.d("Group_test", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

//            override fun onResponse(call: Call<data_create>, response: Response<data_create>) {
//                if(response.isSuccessful){
//                    // 정상적으로 통신이 성공된 경우
//                    var result: data_create? = response.body()
//                    Log.d("YMC", "onResponse 성공: " + result?.toString());
//                }else{
//                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
//                    Log.d("YMC", "onResponse 실패")
//                }
//            }
//
//            override fun onFailure(call: Call<data_create>, t: Throwable) {
//                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
//                Log.d("YMC", "onFailure 에러: " + t.message.toString());
//            }
        })

    }

}