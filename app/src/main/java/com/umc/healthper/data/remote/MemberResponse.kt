package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName(value = "kakaokey") val kakaokey : Long,
    @SerializedName(value = "nickname") val nickname : String,
    @SerializedName(value = "reportedCount") val reportedCount : Int,
    @SerializedName(value = "status") val status : String
)

/**
 * {
    "kakaokey": 2022,
    "nickname": "peter",
    "reportedCount": 0,
    "status": "NORMAL"
}
 */