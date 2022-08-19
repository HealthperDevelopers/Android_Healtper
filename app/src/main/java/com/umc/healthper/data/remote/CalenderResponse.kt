package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class CalenderResponse(
    @SerializedName(value = "day") val day : Int,
    @SerializedName(value = "sections") val sections : List<String>
)