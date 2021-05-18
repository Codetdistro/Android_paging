package com.example.android_paging.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class New(
    val favorite: Int,
    val image_source_link: String,
    val image_source_title: String,
    val member_first_name: String,
    val member_id: String,
    val member_last_name: String,
    val member_like: Int,
    val member_profile_pic: String,
    val member_username: String,
    val news_content: String,
    @PrimaryKey
    val news_id: String,
    val news_image: String,
    val news_publishdate: String,
    val news_title: String,
    val news_url: String,
    val slug: String,
    val tags: String,
    val thumb_image: String,
    val total_comment: String,
    val total_like: Int,
    val total_views: String
)