package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.data.test.data_hash_img
import dev.kichan.daseul.model.data.test.data_invite
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Group {
    @FormUrlEncoded
    @POST("v1/group/create")
    fun makeGroup(
        @Header("Authorization") accessToken: String,
        @Field("name") name: String,
        @Field("image") hashkey : String,
    ): Call<data_create>

    @Multipart
    @POST("/v1/file/upload")
    fun uploadImage(
        @Header("Authorization") accessToken: String,
        @Part image: MultipartBody.Part): Call<data_hash_img>



    @POST("/v1/group/invite/create")
    fun convertInvite(
        @Header("Authorization") accessToken: String,
        @Body dataInviteBody: RequestBody
    ): Call<data_invite>



}