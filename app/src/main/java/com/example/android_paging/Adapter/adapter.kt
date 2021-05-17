package com.example.android_paging.Adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class adapter (private val retry:() -> Unit):LoadStateAdapter<footerViewHolder>() {

    override fun onBindViewHolder(holder: footerViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): footerViewHolder {
        return footerViewHolder.create(parent,retry)
    }
}