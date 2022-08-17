package com.umc.healthper.ui.timer.data

import com.google.gson.annotations.SerializedName

data class Work (
        @SerializedName(value = "totalTime")var totalTime:Int, // 마지막 운동의 토탈 = 진짜 토탈
        @SerializedName(value = "runningTime")var runningTime:Int,
        @SerializedName(value = "pack")var pack: ArrayList<Pack>,
        @SerializedName(value = "partId")var partId:Int,
        @SerializedName(value = "work")var work: String
        )

data class Pack (
        @SerializedName(value = "set")var set:Int, // temp -> 나중에 제거할거임.
        @SerializedName(value = "weight")var weight:Int,
        @SerializedName(value = "count")var count:Int
        )