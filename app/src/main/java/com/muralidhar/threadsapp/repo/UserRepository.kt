package com.muralidhar.threadsapp.repo

import com.muralidhar.threadsapp.room.daos.UserDao
import com.muralidhar.threadsapp.room.entities.Users
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUserList(users: List<Users>) = userDao.insertUsers(users)
    fun getUser(): Flow<Users> = userDao.getUsers()
    fun getUserList(): Flow<MutableList<Users>> = userDao.getUserList()
}