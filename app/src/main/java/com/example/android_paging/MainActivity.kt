package com.example.android_paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_paging.Adapter.adapter
import com.example.android_paging.Adapter.newsAdapter
import com.example.android_paging.db.NewsDatabase
import com.example.android_paging.model.repository
import com.example.android_paging.model.viewModel
import com.example.android_paging.model.viewModelFactory
import com.example.android_paging.network.Api
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    lateinit var  viewModel:viewModel
    private val adpt = newsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val ap = Api()
        val db = NewsDatabase(this)
        val repo = repository(ap,db)
        val factory = viewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(com.example.android_paging.model.viewModel::class.java)

        initAdapter()
        loadData()

        button.setOnClickListener { adpt.retry() }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.getModelNews().collectLatest { pageData->
                adpt.submitData(pageData)

            }
        }

        lifecycleScope.launch {
            adpt.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
    }

    private fun initAdapter() {
        recyclerView.adapter = adpt.withLoadStateHeaderAndFooter(
            header = adapter{adpt.retry()},
            footer = adapter{adpt.retry()}
        )
        adpt.addLoadStateListener { loadState->
            recyclerView.isVisible = loadState.refresh is LoadState.NotLoading
            progressbar.isVisible = loadState.source.refresh is LoadState.Loading
            button.isVisible = loadState.source.refresh is LoadState.Error
        }
    }
}