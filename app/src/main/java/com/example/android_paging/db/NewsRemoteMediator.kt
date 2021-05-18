package com.example.android_paging.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android_paging.data.New
import com.example.android_paging.data.RemoteKey
import com.example.android_paging.network.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val database: NewsDatabase,
    private val networkService: Api
) : RemoteMediator<Int, New>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, New>
    ): MediatorResult {

            val pageKeyData = getKeyPageData(loadType, state)
            val page = when (pageKeyData) {
                is MediatorResult.Success -> {
                    return pageKeyData
                }
                else -> {
                    pageKeyData as Int
                }
            }



        return try {
            val response = networkService.getResult(page)
            val isEndOfList = response.news.isEmpty()
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.newsDao().clearAll()
                        database.remoteDao().deleteByQuery()
                    }
                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val keys = response.news.map {
                        RemoteKey(it.news_id, prevKey = prevKey, nextKey = nextKey)
                    }
                        database.remoteDao().insertOrReplace(keys)
                        database.newsDao().insertAll(response.news)
                }
                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, New>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                prevKey

            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, New>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.news_id?.let { repoId ->
                database.remoteDao().remoteKeyByQuery(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, New>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { cat -> database.remoteDao().remoteKeyByQuery(cat.news_id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, New>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { cat -> database.remoteDao().remoteKeyByQuery(cat.news_id) }
    }
}