package dev.kichan.daseul.model.data.test

import java.util.Objects

data class RegisterReq (
    val name : String,
    val phone : String,
    val token : kakaoOauth,
    )