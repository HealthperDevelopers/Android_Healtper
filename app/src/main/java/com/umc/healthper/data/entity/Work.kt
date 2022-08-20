package com.umc.healthper.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "defaultWorkTable")
data class Work (
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
var workName: String = "",
var workPartId: Int = 0,
var workWeight: Int = 0,
var workTime: Int = 0,
var workSet: Int = 0
        )
