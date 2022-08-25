package com.umc.healthper.data.entity

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class User (
    @SerializedName(value = "kakaoIdx") var kakaoIdx : BigInteger
)