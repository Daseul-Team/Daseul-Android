package dev.kichan.daseul.ui.main.login

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dev.kichan.daseul.BuildConfig.kakao_native_api_key
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.ActivityRegisterBinding
import dev.kichan.daseul.model.data.test.kakaoOauth
import dev.kichan.daseul.model.service.KaKaoservice
import dev.kichan.daseul.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private val mContext: Context = this
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var Username:String
    private lateinit var Usernumber:String


    private fun sendoath(useroauthaccess : String, useroauthrefresh : String,) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.daseul.deltalab.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(KaKaoservice::class.java)
        val userdat = kakaoOauth(useroauthaccess, useroauthrefresh)

        /*CoroutineScope(Dispatchers.IO).launch {
            try {*/
                service.postuseroauth(userdat).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("dasulapi", "API FAIL")
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("dasulapi", response.body().toString())
                    }
                })
        /*}
        catch (e: Exception){
            Log.e("service cor", e.message.toString())
        }
        }*/


    }
    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.to_right, R.anim.from_right)
            .replace(R.id.fragment, fragment)
            .commit()
    }
    fun savename(name: String) {
        Username = name
    }
    fun savenumber(phonenumber: String) {
        Usernumber = phonenumber
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        KakaoSdk.init(this, kakao_native_api_key)

        binding.btnKakaologin.setOnClickListener {

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    sendoath(token.accessToken, token.refreshToken)
                    replaceFragment(Register1Fragment())
                }
            }
            val context = this
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent/*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/)
                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }
}