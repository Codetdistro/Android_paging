package com.example.android_paging.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_paging.R
import com.example.android_paging.data.New
import kotlinx.android.synthetic.main.recycler_item.view.*

class newsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val news: New? = null

    fun bind(news:New?){
        if (news == null) {

        }else{
            showData(news)
        }
    }

    private fun showData(news: New) {
        itemView.title.text = news.news_title
        itemView.content.text = news.news_content
        itemView.viewsEye.text = news.total_views
        itemView.comment.text = news.total_comment
        Glide.with(itemView)
            .load(news.news_image)
            .into(itemView.news_image)
    }

    companion object{
        fun create(parent:ViewGroup):newsViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item,parent,false)
            return newsViewHolder(view)
        }
    }


}