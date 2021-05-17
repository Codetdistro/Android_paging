package com.example.android_paging.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android_paging.data.New
import com.example.android_paging.network.Api
import kotlinx.coroutines.flow.Flow

class repository(private val ap:Api) {

    fun getResult(): Flow<PagingData<New>>{
        return Pager(
            config = PagingConfig(pageSize = 10,enablePlaceholders = false),
            pagingSourceFactory = {DataSource(ap) }
        ).flow
    }
}