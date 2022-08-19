package com.umc.healthper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umc.healthper.data.entity.WorkPart


@Dao
interface WorkPartDao {
    @Insert
    fun insert(data: WorkPart)

    @Query("SELECT id FROM workPartTable WHERE workPart = :workPart")
    fun getWorkPartIdbyPartName(workPart: String): Int

    @Query("SELECT workPart FROM workPartTable")
    fun getAllWork(): List<String>

    @Query("SELECT * FROM workPartTable WHERE id = 1")
    fun getFirst(): WorkPart
}