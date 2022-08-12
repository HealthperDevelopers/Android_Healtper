package com.umc.healthper.ui.timer.data

data class Work (
        var totalTime:Int,
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