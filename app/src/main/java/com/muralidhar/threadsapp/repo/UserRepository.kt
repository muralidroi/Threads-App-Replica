package com.muralidhar.threadsapp.repo

import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun insertUserList(users: List<Users>): List<Long>
    fun getUser(): Flow<Users>
    fun getUserList(): Flow<ResultState<MutableList<Users>>>
}