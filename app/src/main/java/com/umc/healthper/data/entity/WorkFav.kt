package com.umc.healthper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workFavTable")
data class WorkFav (
    @PrimaryKey(autoGenerate = true)var id: Int = 0,
    var order: Int = 0,
    var workId: Int = 0,
    var workPartId: Int = 0
        )