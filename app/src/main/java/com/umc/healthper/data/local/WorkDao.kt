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
    fun findWorkbyPartId(id: Int): List<Work>

    @Query("SELECT * FROM defaultWorkTable WHERE id = :id")
    fun findWorkbyId(id: Int): Work

    @Query("SELECT workPartId FROM defaultWorkTable WHERE id = :id")
    fun findWorkPartbyId(id: Int): Int

    @Query("SELECT workPartId FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkPartbyWorkName(WorkName: String): Int

    @Query("SELECT id FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkIDbyWorkName(WorkName: String): Int

    @Query("SELECT workWeight FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkWeightbyWorkName(WorkName: String): Int

    @Query("SELECT workTime FROM defaultWorkTable WHERE workName = :WorkName")
    fun findWorkCountbyWorkName(WorkName: String): Int

    @Query("UPDATE defaultWorkTable SET workWeight = :WorkWeight WHERE workName = :WorkName ")
    fun updateWorkWeight(WorkName: String, WorkWeight : Int)

    @Query("UPDATE defaultWorkTable SET workTime = :WorkCount WHERE workName = :WorkName ")
    fun updateWorkCount(WorkName: String, WorkCount : Int)
}