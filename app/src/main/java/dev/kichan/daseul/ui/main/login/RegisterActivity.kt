package dev.kichan.daseul.ui.main.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dev.kichan.daseul.BuildConfig
import dev.kichan.daseul.R
import dev.kichan.daseul.databinding.ActivityRegisterBinding
import dev.kichan.daseul.ui.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private val mContext: Context = this
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        KakaoSdk.init(this, "26bbb798fbce3d95935ccc85721c8322")

        binding.btnKakaologin.setOnClickListener {

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent/*.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)*/)
                    finish()
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