package com.umc.healthper.data.remote

import com.google.gson.annotations.SerializedName

data class Test(
    @SerializedName (value="str") val str : String
)