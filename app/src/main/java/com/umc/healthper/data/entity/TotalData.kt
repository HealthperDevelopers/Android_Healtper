package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName
import com.umc.healthper.ui.timer.data.Work

data class TotalData (
    @SerializedName(value = "comment") var comment : String,
    @SerializedName(value = "sections") var sections : ArrayList<String>,
    @SerializedName(value = "exerciseInfo") var exerciseInfo : ExerciseInfo,
    )

data class ExerciseInfo (
    @SerializedName(value = "totalExerciseTime") var totalExerciseTime : Int,
    @SerializedName(value = "totalVolume") var totalVolume : Int
    )