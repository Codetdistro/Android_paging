package com.example.android_paging.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android_paging.data.New
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class viewModel(val repository: repository): ViewModel() {

    val newsResult: Flow<PagingData<New>> = repository.getResult().cachedIn(viewModelScope)

    fun getModelNews():Flow<PagingData<New>>{
        return newsResult
    }
}