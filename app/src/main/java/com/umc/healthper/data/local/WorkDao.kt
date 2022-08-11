package com.umc.healthper.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.umc.healthper.data.entity.Work

@Dao
interface WorkDao {
    @Query("SELECT * FROM defaultWorkTable WHERE id = 1")
    fun getFirst(): Work

    @Insert
    fun insert(data: Work)

    @Query("SELECT * FROM defaultWorkTable WHERE workPartId = :id")
    fun findWorkbyId(id: Int): List<Work>

    @Query("SELECT workPartId FROM defaultWorkTable WHERE id = :id")
    fun findWorkPartbyId(id: Int): Int

    @Query("SELECT id FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkIDbyWorkName(WorkName: String): Int
}