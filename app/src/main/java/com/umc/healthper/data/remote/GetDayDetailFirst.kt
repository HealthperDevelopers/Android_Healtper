package com.umc.healthper.data.remote

data class GetDayDetailFirst (
        var record_id: Int = 0,
        var comment: String? = null,
        var sections: ArrayList<String>? = null,
        var exerciseEntity: ExerciseEntity? = null
)

data class ExerciseEntity (
        var totalExerciseTime:Int = 0,
        var totalVolume:Int = 0
        )
