package com.umc.healthper.data.local

import androidx.room.*
import com.umc.healthper.data.entity.PrivacyInfo

@Dao
interface PrivacyInfoDao {
    @Insert
    fun insert(privacyInfo: PrivacyInfo)

    @Update
    fun update(privacyInfo: PrivacyInfo)

    @Delete
    fun delete(privacyInformation: PrivacyInfo)

    @Query("SELECT * FROM PrivacyInfoTable")
    fun getPrivacyInfo(): List<PrivacyInfo>
}
