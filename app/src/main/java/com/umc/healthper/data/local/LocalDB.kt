package com.umc.healthper.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.entity.WorkFav
import com.umc.healthper.data.entity.WorkPart

@Database(entities = [Work::class, WorkPart::class, WorkFav::class], version = 3)
abstract class LocalDB: RoomDatabase() {
    abstract fun WorkDao(): WorkDao
    abstract fun WorkPartDao(): WorkPartDao
    abstract fun WorkFavDao(): WorkFavDao

    companion object {
        private var instance: LocalDB? = null

        @Synchronized
        fun getInstance(context: Context): LocalDB? {
            if (instance == null) {
                synchronized(LocalDB::class) {//synchronized block-> 클래스자체를 동기화->클래스 사용해 생성하는 모든 쓰레드 동기화
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDB::class.java,
                        "local_database"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }

}