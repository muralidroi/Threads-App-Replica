package com.muralidhar.threadsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muralidhar.threadsapp.repo.UserRepository
import com.muralidhar.threadsapp.room.entities.Users
import com.muralidhar.threadsapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.Date

class UserViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _insertUser = MutableLiveData<List<Long>>()
    val insertUser: LiveData<List<Long>>
        get() = _insertUser

    private val _getUserList = MutableLiveData<ResultState<MutableList<Users>>>()
    val getUserList: LiveData<ResultState<MutableList<Users>>>
        get() = _getUserList

    private val _getUser = MutableLiveData<Users>()
    val getUser: LiveData<Users>
        get() = _getUser

    fun insertUser() {
        val user1 = Users(
            name = "Muralidhar",
            userDescription = "iamMurali",
            followersCount = 170,
            email = "email",
            bioInfo = "yad bhavam tad bhavati",
            createdAt = Date().time.toString(),
            imageName = "murali1"
        )
        val user2 = Users(
            name = "user1",
            userDescription = "userName1",
            followersCount = 250,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = ""
        )
        val user3 = Users(
            name = "user2",
            userDescription = "userName2",
            followersCount = 100,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = "murali"
        )
        val user4 = Users(
            name = "user3",
            userDescription = "userName3",
            followersCount = 200,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = "murali2"
        )
        val user5 = Users(
            name = "user4",
            userDescription = "userName4",
            followersCount = 90,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = ""
        )
        val user6 = Users(
            name = "user5",
            userDescription = "userName5",
            followersCount = 10,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = "murali3"
        )
        val user7 = Users(
            name = "user6",
            userDescription = "userName6",
            followersCount = 20,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = ""
        )
        val user8 = Users(
            name = "TestUser2",
            userDescription = "App is written in compose",
            followersCount = 50,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = "murali4"
        )
        val user9 = Users(
            name = "TestUser1",
            userDescription = "Beautiful day of the year",
            followersCount = 30,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = ""
        )
        val user10 = Users(
            name = "Muralidhar",
            userDescription = "Hi this is sample text",
            followersCount = 70,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time.toString(),
            imageName = "murali5"
        )
        val userList = listOf(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)

        viewModelScope.launch(Dispatchers.IO) {
            val insertUser = userRepo.insertUserList(userList)
            _insertUser.postValue(insertUser)
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val getUser = userRepo.getUser()
            getUser.collect {
                _getUser.postValue(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUserList()
                .flatMapConcat { resultState ->
                    when (resultState) {
                        is ResultState.Loading -> {
                            flowOf(ResultState.Loading)
                        }

                        is ResultState.Success -> {
                            val userList = resultState.data
                            userList.map { user ->
                                getUserImageUrlFirebase(user.imageName)
                                    .map { imageUrl ->
                                        user.apply {
                                            if (imageUrl != null) this.imageName = imageUrl
                                            val timeSincePosted =
                                                (Date().time - user.createdAt.toLong())
                                            val hrs = (timeSincePosted / (1000 * 60 * 60))
                                            val dayString: String = if (hrs > 24) {
                                                " ${hrs / 24} d"
                                            } else " $hrs h"
                                            this.createdAt = dayString
                                        }
                                    }
                            }
                                .asFlow()
                                .flatMapConcat { it }
                                .toList()
                                .let { updatedUserList ->
                                    flowOf(ResultState.Success(updatedUserList.toMutableList()))
                                }
                        }

                        is ResultState.Failure -> {
                            flowOf(ResultState.Failure(resultState.msg))
                        }
                    }
                }
                .collect { updatedResultState ->
                    _getUserList.postValue(updatedResultState)
                }
        }
    }

    private fun getUserImageUrlFirebase(userImageName: String): Flow<String?> = callbackFlow {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference(userImageName)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val map = snapshot.value

                if (userImageName == snapshot.key) {
                    trySend(map.toString()).isSuccess
                } else {
                    trySend(null).isSuccess
                }
                close()
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(null).isSuccess
                close(error.toException())
            }
        }

        databaseReference.addValueEventListener(listener)
        awaitClose { databaseReference.removeEventListener(listener) }
    }
}