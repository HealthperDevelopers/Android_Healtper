package com.umc.healthper.data.remote

data class GetDayDetailSecond (
    var exerciseName: String? = null,
    var section: String,
    var exerciseTime : Int,
    var details: ArrayList<Detail>? = null
    )

data class Detail (
    var setNumber : Int,
    var repeatTime : Int,
    var weight : Int
    )
