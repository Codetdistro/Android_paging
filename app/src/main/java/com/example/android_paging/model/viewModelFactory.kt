package com.example.android_paging.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi

@ExperimentalPagingApi
class viewModelFactory(private val repo:repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return viewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}