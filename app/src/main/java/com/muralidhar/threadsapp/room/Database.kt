package com.muralidhar.threadsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muralidhar.threadsapp.room.daos.UserDao
import com.muralidhar.threadsapp.room.entities.Users

@Database(
    entities = [Users::class],
    version = 1, exportSchema = true
)
abstract class Database : RoomDatabase() {

    abstract fun getUsersDao(): UserDao

}