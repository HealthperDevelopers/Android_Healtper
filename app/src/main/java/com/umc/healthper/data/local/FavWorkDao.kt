package com.umc.healthper.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.umc.healthper.data.entity.FavWork

@Dao
interface FavWorkDao {
    @Query("SELECT * FROM FavWorkTable ORDER BY id DESC")
    fun getAll(): LiveData<List<FavWork>>
    // 전체 데이터에 변화가 생길때 LiveData Callback을 실행하여 UI를 업데이트

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(FavWork: FavWork)

    @Update
    fun update(FavWork: FavWork)

    @Delete
    fun delete(FavWork: FavWork)

    @Query("DELETE FROM FavWorkTable")
    fun deleteAll()
}