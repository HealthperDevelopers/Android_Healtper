package com.umc.healthper.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavWorkTable")
data class FavWork (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var part : String = "",
    var ascending: Int = 0,
    var FavWorkPartId : Int = 0,
    var FavWorkPartName : String = ""
)