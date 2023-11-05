package dev.kichan.daseul.model.service

import com.google.gson.JsonArray
import dev.kichan.daseul.model.data.test.data_create
import dev.kichan.daseul.model.data.test.data_hash_img
import dev.kichan.daseul.model.data.test.data_infoGroup
import dev.kichan.daseul.model.data.test.data_invite
import dev.kichan.daseul.model.data.test.getReturnUserInfo
import dev.kichan.daseul.model.data.test.getuserInfo
import dev.kichan.daseul.model.data.test.joinGroup
import dev.kichan.daseul.model.data.test.make_group
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface Group {

    @POST("group/create")
    fun makeGroup(
        @Header("Authorization") accessToken: String,
        @Body makeGroup: make_group
    ): Call<data_create>

    @Multipart
    @POST("file/upload")
    fun uploadImage(
        @Header("Authorization") accessToken: String,
        @Part image: MultipartBody.Part): Call<data_hash_img>



    @POST("group/invite/create")
    fun convertInvite(
        @Header("Authorization") accessToken: String,
        @Body dataInviteBody: RequestBody
    ): Call<data_invite>

    @GET("group/list")
    fun getListGroupId(
        @Header("Authorization") accessToken: String
    ): Call<JsonArray>

    @GET("group/info/{pathValue}")
    fun getGroupInfo(
        @Header("Authorization") accessToken: String,
        @Path("pathValue") pathValue : String): Call<data_infoGroup>

    @POST("group/invite/join")
    fun joinGroup(
        @Header("Authorization") accessToken: String,
        @Body joinGroup: joinGroup
    ):Call<Void>

    @POST("group/participant/info")
    fun getuserinfo(
        @Header("Authorization") accessToken: String,
        @Body requestBody: getuserInfo
    ):Call<getReturnUserInfo>
}