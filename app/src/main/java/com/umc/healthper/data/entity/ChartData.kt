package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName

data class ChartData (
    @SerializedName(value = "chart") var chart : List<InChart>,
    @SerializedName(value = "totalVolume") var totalVolume : Int,
    @SerializedName(value = "totalTime") var totalTime : Int
)

data class InChart (
    @SerializedName(value = "date") var date : String,
    @SerializedName(value = "volume") var volume : Int
    )