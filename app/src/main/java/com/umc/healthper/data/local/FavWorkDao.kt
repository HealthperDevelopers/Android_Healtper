package com.umc.healthper.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umc.healthper.data.entity.FavWork

@Dao
interface FavWorkDao {
    @Query("SELECT * FROM favworktable ORDER BY id DESC")
    fun getAll(): LiveData<List<FavWork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favwork: FavWork)

    @Update
    fun update(favwork: FavWork)

    @Delete
    fun delete(favwork: FavWork)

    @Query("DELETE FROM favworktable")
    fun deleteAll()
}