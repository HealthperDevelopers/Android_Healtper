package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName
import com.umc.healthper.ui.timer.data.Work

data class TotalData (
    @SerializedName(value = "workList") var workList: ArrayList<Work>,
    @SerializedName(value = "comment") var comment : String
)