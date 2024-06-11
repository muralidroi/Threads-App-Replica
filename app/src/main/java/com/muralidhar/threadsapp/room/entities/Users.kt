package com.muralidhar.threadsapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Users(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int = 0,

    @ColumnInfo(name = "NAME")
    val name: String,

    @ColumnInfo(name = "USER_NAME")
    val userName: String,

    @ColumnInfo(name = "FOLLOWERS_COUNT")
    val followersCount: Int,

    @ColumnInfo(name = "EMAIL")
    val email: String,

    @ColumnInfo(name = "BIO")
    val bioInfo: String,

    @ColumnInfo(name = "CREATED_AT")
    val createdAt: Long,

    @ColumnInfo(name = "IMAGE_NAME")
    val imageName: String,
    )