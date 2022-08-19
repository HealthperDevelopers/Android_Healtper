package com.umc.healthper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umc.healthper.data.entity.WorkFav

@Dao
interface WorkFavDao {
    @Insert
    fun insert(data: WorkFav)

    @Query("SELECT * FROM workFavTable WHERE workId = :id")
    fun getAllFavWorkById(id: Int): List<WorkFav>

    @Query("SELECT * FROM workFavTable WHERE workPartId= :part")
    fun getAllFavWorkByPartId(part: Int): List<WorkFav>
}