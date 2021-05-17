package com.example.android_paging.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.android_paging.R
import kotlinx.android.synthetic.main.item_list_footer.view.*

class footerViewHolder(view: View,retry:()-> Unit):RecyclerView.ViewHolder(view) {

    init {
        itemView.retry_button.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState){
        if(loadState is LoadState.Error){
            itemView.error_msg.text = "There is something Worng Please Try again!!"
        }else{
            itemView.progress_bar.isVisible = loadState is LoadState.Loading
            itemView.retry_button.isVisible = loadState !is LoadState.Loading
        }
    }

    companion object{
        fun create(parent:ViewGroup,retry: () -> Unit):footerViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer,parent,false)
            return footerViewHolder(view,retry)

        }
    }
}