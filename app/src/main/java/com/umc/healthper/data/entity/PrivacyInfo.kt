package com.umc.healthper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "PrivacyInfoTable")
data class PrivacyInfo(
        @SerializedName("title") var title: String?,
        @SerializedName("content") var content: String
    ) {
        @PrimaryKey(autoGenerate = true) var idx: Int = 0
}
