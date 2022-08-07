package com.umc.healthper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "defaultWorkTable")
data class Work (
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
var workName: String = "",
var workPart: String = "",
var workWeight: Int = 0,
var workTime: Int = 0,
var workSet: Int = 0
        )
