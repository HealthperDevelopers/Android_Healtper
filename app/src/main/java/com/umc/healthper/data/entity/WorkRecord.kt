package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName

data class WorkRecord (
        @SerializedName(value = "exerciseTime") var runningTime:Int,
        @SerializedName(value = "details") var pack: ArrayList<Pack>,
        @SerializedName(value = "section") var partId:Int,
        @SerializedName(value = "exerciseName") var work: String
        )

data class Pack (
        @SerializedName(value = "setNumber") var set:Int, // temp -> 나중에 제거할거임.
        @SerializedName(value = "weight") var weight:Int,
        @SerializedName(value = "repeatTime") var count:Int
        )