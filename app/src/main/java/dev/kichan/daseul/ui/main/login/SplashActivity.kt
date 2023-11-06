package dev.kichan.daseul.ui.main.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.JsonArray
import dev.kichan.daseul.R
import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.service.Group
import dev.kichan.daseul.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private val retrofit = RetrofitClient.getRetrofit()

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
            val service = retrofit.create(Group::class.java)
            val Token = "Bearer "+getSharedPreferences("Group_Info", 0).getString("token", "")
            Log.d("fortest","자동 로그인 토큰 = "+Token)
            val call: Call<JsonArray> = service.getListGroupId(Token)
            call.enqueue(object : Callback<JsonArray> {
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SplashActivity,"자동 로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // 요청이 실패한 경우
                        val body = response.errorBody()!!.string()
                        Log.d("Group_test", "error - body : $body")
                    }
                }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    Toast.makeText(this@SplashActivity,"자동 로그인 실패", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SplashActivity, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            })
    }
}