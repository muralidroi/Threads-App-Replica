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

    @ColumnInfo(name = "USER_DESCRIPTION")
    val userDescription: String,

    @ColumnInfo(name = "FOLLOWERS_COUNT")
    val followersCount: Int,

    @ColumnInfo(name = "EMAIL")
    val email: String,

    @ColumnInfo(name = "BIO")
    val bioInfo: String,

    @ColumnInfo(name = "CREATED_AT")
    var createdAt: String,

    @ColumnInfo(name = "IMAGE_NAME")
    var imageName: String,
    )