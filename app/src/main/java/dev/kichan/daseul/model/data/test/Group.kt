package dev.kichan.daseul.model.data.test

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class data_create(
    @SerializedName("id")
    val id: UUID

)
data class data_invite(
    @SerializedName("group_id")
    val group_id: UUID,

    @SerializedName("id")
    val id: String
)
data class data_info(
    @SerializedName("group_id")
    val group_id: UUID,

    @SerializedName("group_name")
    val group_name: String,

    @SerializedName("invited_by")
    val invited_by: UUID,

    @SerializedName("invited_by_name")
    val invited_by_name: UUID

)