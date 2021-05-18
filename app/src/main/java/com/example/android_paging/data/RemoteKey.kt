package com.example.android_paging.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val id: String, val prevKey:Int?, val nextKey: Int?)