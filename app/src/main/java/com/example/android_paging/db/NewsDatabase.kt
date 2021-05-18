package com.example.android_paging.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android_paging.data.New
import com.example.android_paging.data.RemoteKey

@Database(
    entities = [New::class,RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase:RoomDatabase() {

    abstract fun newsDao():NewsDao
    abstract fun remoteDao():RemoteDao

    companion object{
        @Volatile private var instance:NewsDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news"
        ).build()
    }
}