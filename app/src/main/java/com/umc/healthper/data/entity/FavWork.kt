package com.umc.healthper.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavWorkTable")
data class FavWork (
    @PrimaryKey(autoGenerate = true) var id: Int = 0, // ascending
    var part : String = "", // partId
    var ascending: Int = 0,
    var FavWorkPartId : Int = 0, // WorkId
    var FavWorkPartName : String = ""
)