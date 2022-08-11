package com.umc.healthper.ui.timer.data

data class Work (
        var totalTime:Int,
        var runningTime:Int,
        var pack: ArrayList<Pack>,
        var part:String
        )

data class Pack (
        var set:Int,
        var weight:Int,
        var count:Int
        )