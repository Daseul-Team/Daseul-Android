package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.CommonRes
import dev.kichan.daseul.model.data.test.RegisterReq
import dev.kichan.daseul.model.data.test.kakaoOauth
import dev.kichan.daseul.model.data.test.kakaoOauthres
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KaKaoservice {
    @POST("/v1/account/oauth/kakao")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postuseroauth(@Body body : kakaoOauth) : Call<kakaoOauthres>

    @POST("/v1/account/register/kakao")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun UserRegister(@Body body : RegisterReq) : Call<CommonRes>
}