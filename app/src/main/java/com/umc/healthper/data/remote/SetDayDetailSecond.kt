package com.umc.healthper.data.remote

data class SetDayDetailSecond (
    var exerciseName: String? = null,
    var sectionId: Int,
    var exerciseTime : Int,
    var details: ArrayList<Detail>? = null
    )

