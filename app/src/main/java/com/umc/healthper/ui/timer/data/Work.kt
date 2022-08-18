package com.umc.healthper.ui.timer.data

import com.google.gson.annotations.SerializedName

data class Work (
        var runningTime:Int,
        var pack: ArrayList<Pack>,
        var partId:Int,
        var work: String
        )

data class Pack (
        var set:Int, // temp -> 나중에 제거할거임.
        var weight:Int,
        var count:Int
        )