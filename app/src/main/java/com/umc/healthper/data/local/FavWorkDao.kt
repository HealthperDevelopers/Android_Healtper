package com.umc.healthper.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umc.healthper.data.entity.FavWork

@Dao
interface FavWorkDao {
    @Query("SELECT * FROM favworktable WHERE part = :Part ORDER BY id DESC")
    fun getAll(Part : String): List<FavWork>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(FavWork: FavWork)

    @Update
    fun update(FavWork: FavWork)

    @Delete
    fun delete(FavWork: FavWork)

    @Query("DELETE FROM FavWorkTable")
    fun deleteAll()
}