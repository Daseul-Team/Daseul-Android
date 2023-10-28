package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.data_create
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.POST

interface Group {
    @POST("v1/group/create")
    fun getUserPage(
        @Header("Authorization") accessToken: String,
        @Body name: String
    ): Call<data_create>

}