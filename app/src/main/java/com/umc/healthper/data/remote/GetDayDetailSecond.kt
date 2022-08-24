package com.umc.healthper.data.remote

data class GetDayDetailSecond (
    var exerciseName: String? = null,
    var sectionId: Int,
    var exerciseTime : Int,
    var details: ArrayList<Detail>? = null
    )

data class Detail (
    var setNumber : Int,
    var repeatTime : Int,
    var weight : Int
    )
