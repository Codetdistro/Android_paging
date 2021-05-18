package com.example.android_paging.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_paging.data.New
import com.example.android_paging.db.NewsDatabase
import com.example.android_paging.db.NewsRemoteMediator
import com.example.android_paging.network.Api
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class repository(private val ap:Api, private val db:NewsDatabase) {


    fun getResult(): Flow<PagingData<New>>{
        val pagingSourceFactory = { db.newsDao().pagingSource() }
        return Pager(
            config = PagingConfig(pageSize = 10,enablePlaceholders = false),
            remoteMediator = NewsRemoteMediator(db,ap),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}