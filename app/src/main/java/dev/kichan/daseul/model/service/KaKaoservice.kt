package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.kakaoOauth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KaKaoservice {
    @POST("/v1/account/oauth/kakao")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun postuseroauth(@Body body : kakaoOauth) : Call<String>
}