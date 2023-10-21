package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.TestReq
import dev.kichan.daseul.model.data.test.TestRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Oauth {
    @POST("/v1/account/oauth/kakao")
    @Headers("accept: application/json",
        "content-type: application/json")
    suspend fun postuseroauth(@Body body : String) : Call<String>
}