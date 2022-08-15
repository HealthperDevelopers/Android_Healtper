package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "day") val day : Int,
    @SerializedName(value = "sections") val sections : List<String>
)