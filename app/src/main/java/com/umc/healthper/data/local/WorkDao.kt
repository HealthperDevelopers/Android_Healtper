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
<<<<<<< HEAD
    fun findWorkbyId(id: Int): List<Work>

    @Query("SELECT workPartId FROM defaultWorkTable WHERE id = :id")
    fun findWorkPartbyId(id: Int): Int

    @Query("SELECT workPartId FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkPartbyWorkName(WorkName: String): Int

    @Query("SELECT id FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkIDbyWorkName(WorkName: String): Int

    @Query("SELECT workWeight FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkWeightbyWorkName(WorkName: String): Int

    @Query("SELECT workSet FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkSetbyWorkName(WorkName: String): Int
=======
    fun findWorkbyPartId(id: Int): List<Work>

    @Query("SELECT * FROM defaultWorkTable WHERE id = :id")
    fun findWorkbyId(id: Int): Work
>>>>>>> workFlow
}