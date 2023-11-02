package dev.kichan.daseul.model.data.test

import com.google.gson.annotations.SerializedName


data class kakaoOauthres(
    @SerializedName("token")
    val access_token : String,
)
