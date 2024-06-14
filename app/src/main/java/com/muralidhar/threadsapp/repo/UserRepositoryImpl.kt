package com.muralidhar.threadsapp.repo

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muralidhar.threadsapp.room.daos.UserDao
import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override fun insertUserList(users: List<Users>) = userDao.insertUsers(users)

    override fun getUser(): Flow<Users> = userDao.getUsers()

    override fun getUserList(): Flow<ResultState<MutableList<Users>>> = flow {
        emit(ResultState.Loading)

        try {
            userDao.getUserList()
                .catch { exception ->
                    emit(ResultState.Failure(exception))
                }
                .collect { userList ->
                    emit(ResultState.Success(userList))
                }
        } catch (e: Exception) {
            emit(ResultState.Failure(e))
        }
    }
}