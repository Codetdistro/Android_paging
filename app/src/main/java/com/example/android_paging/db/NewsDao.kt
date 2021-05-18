package com.example.android_paging.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_paging.data.New

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<New>)

    @Query("SELECT * FROM news")
    fun pagingSource(): PagingSource<Int, New>

    @Query("DELETE FROM news")
    suspend fun clearAll()
}