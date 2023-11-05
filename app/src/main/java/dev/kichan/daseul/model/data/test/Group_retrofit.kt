package dev.kichan.daseul.model.data.test

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class data_create(
    @SerializedName("id")
    val id: UUID
)
data class make_group(
    var name : String,
    var image : String
)
data class data_hash_img(
    @SerializedName("hash")
    val hash: String
)
data class data_invite(
    @SerializedName("token")
    val invite_id: String,
)
data class data_invite_body(
    val group_id: String
)
data class joinGroup(
    val token: String
)
data class data_infoGroup(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("participants")
    var who: List<String>,

    val group_id: String
)
data class recycle_item(
    var imageHash : String,
    var name : String
)
data class getuserInfo(
    val group_id: String,
    val participant_id: String
)
data class getReturnUserInfo(
    @SerializedName("name")
    var name : String,
    @SerializedName("owner")
    var owner : Boolean
)