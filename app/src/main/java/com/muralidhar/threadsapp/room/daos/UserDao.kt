package com.muralidhar.threadsapp.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muralidhar.threadsapp.room.entities.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insertUsers(user: List<Users>): List<Long>

    @Query("select * from Users limit 1")
    fun getUsers(): Flow<Users>

    @Query("select * from Users ORDER BY ID DESC")
    fun getUserList(): Flow<MutableList<Users>>
}