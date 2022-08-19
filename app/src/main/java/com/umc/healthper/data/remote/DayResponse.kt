package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class DayResponse (
        @SerializedName(value = "record_id") var record_id:Int,
        @SerializedName(value = "comment") var comment:String,
        @SerializedName(value = "total_exercise_time") var total_exercise_time:Int,
        @SerializedName(value = "total_volume") var total_volume: Int,
        @SerializedName(value = "section") var section:List<String>
)