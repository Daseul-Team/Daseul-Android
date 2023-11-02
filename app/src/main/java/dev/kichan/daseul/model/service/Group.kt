package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.data.test.data_hash_img
import dev.kichan.daseul.model.data.test.data_invite
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.UUID


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

    @FormUrlEncoded
    @POST("/v1/group/invite/create")
    fun convertInvite(
        @Header("Authorization") accessToken: String,
        @Field("group_id") hashkey : UUID,
    ): Call<data_invite>




}