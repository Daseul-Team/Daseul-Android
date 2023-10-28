package dev.kichan.daseul.ui.main.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dev.kichan.daseul.BuildConfig.kakao_native_api_key
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.ActivityRegisterBinding
import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.data.test.CommonRes
import dev.kichan.daseul.model.data.test.RegisterReq
import dev.kichan.daseul.model.data.test.kakaoOauth
import dev.kichan.daseul.model.data.test.kakaoOauthres
import dev.kichan.daseul.model.service.KaKaoservice
import dev.kichan.daseul.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.math.log


class RegisterActivity : AppCompatActivity() {
    private val mContext: Context = this
    private lateinit var binding: ActivityRegisterBinding
    lateinit var Username:String
    private lateinit var Usernumber:String
    private lateinit var useroauthaccess: String
    private lateinit var useroauthrefresh: String
    fun registerpost() {
        val service = RetrofitClient.getRetrofit().create(KaKaoservice::class.java)

        val useroauthob = kakaoOauth(useroauthaccess, useroauthrefresh)
        val userdat = RegisterReq(Username, Usernumber, useroauthob)

        service.UserRegister(userdat).enqueue(object : Callback<CommonRes> {
            override fun onFailure(call: Call<CommonRes>, t: Throwable) {
                Log.d("dasulapi", "API FAIL: ${call}")
            }
            override fun onResponse(
                call: Call<CommonRes>,
                response: Response<CommonRes>
            ) {
                if (response.isSuccessful()) {
                    Log.d("dasulapi", "isSuccessful() : " + response.code());
                }
                else
                {
                    try { val body = response.errorBody()!!.string()
                        Log.d("dasulapi", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
    fun loginpost() {
        val loginservice = RetrofitClient.getRetrofit().create(KaKaoservice::class.java)

        val useroauth = kakaoOauth(useroauthaccess, useroauthrefresh)

        loginservice.postuseroauth(useroauth).enqueue(object : Callback<kakaoOauthres> {
            override fun onFailure(call: Call<kakaoOauthres>, t: Throwable) {
                Log.d("dasulapi", "API FAIL: ${call}")
            }
            override fun onResponse(
                call: Call<kakaoOauthres>,
                response: Response<kakaoOauthres>
            ) {
                if (response.isSuccessful()) {
                    Log.d("dasulapi", "isSuccessful() : " + response.code());
                    Log.d("fortest", response.body().toString())
                    if (response.code() == 404)
                    {
                        replaceFragment(Register1Fragment())
                    }
                    else if (response.code() == 200)
                    {
                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent/*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/)
                        finish()
                    }
                }
                else
                {
                    try {
                        if (response.code() == 404)
                        {
                            replaceFragment(Register1Fragment())
                        }
                        if (response.code() == 200)
                        {
                            val intent = Intent(mContext, MainActivity::class.java)
                            startActivity(intent/*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/)
                            finish()
                        }
                        val body = response.errorBody()!!.string()
                        Log.d("dasulapi", "error - body : $body")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
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

                    useroauthaccess= token.accessToken
                    useroauthrefresh =  token.refreshToken
                    Log.d("fortest","결과 = "+token.accessToken)
                    loginpost()
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
                        useroauthaccess= token.accessToken
                        useroauthrefresh =  token.refreshToken

                        val sharedPreference = getSharedPreferences("Group_Info", 0)
                        val editor = sharedPreference.edit()
                        editor.putString("token",token.accessToken)
                        editor.apply()

                        loginpost()
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")

                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }
}