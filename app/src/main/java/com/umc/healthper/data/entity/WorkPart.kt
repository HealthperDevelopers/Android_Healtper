package com.umc.healthper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workPartTable")
data class WorkPart(
    @PrimaryKey var id: Int = 0,
    var workPart: String = "",
    var color: String = ""
)
