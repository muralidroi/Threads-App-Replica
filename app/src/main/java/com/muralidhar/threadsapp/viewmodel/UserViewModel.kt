package com.muralidhar.threadsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muralidhar.threadsapp.repo.UserRepository
import com.muralidhar.threadsapp.room.entities.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class UserViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _insertUser = MutableLiveData<List<Long>>()
    val insertUser: LiveData<List<Long>>
        get() = _insertUser

    fun insertUser() {
        val user1 = Users(
            name = "Muralidhar",
            userName = "iamMurali",
            followersCount = 170,
            email = "email",
            bioInfo = "yad bhavam tad bhavati",
            createdAt = Date().time,
            imageName = "murali1"
        )
        val user2 = Users(
            name = "user1",
            userName = "userName1",
            followersCount = 250,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = ""
        )
        val user3 = Users(
            name = "user2",
            userName = "userName2",
            followersCount = 100,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = "murali"
        )
        val user4 = Users(
            name = "user3",
            userName = "userName3",
            followersCount = 200,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = "murali2"
        )
        val user5 = Users(
            name = "user4",
            userName = "userName4",
            followersCount = 90,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = ""
        )
        val user6 = Users(
            name = "user5",
            userName = "userName5",
            followersCount = 10,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = "murali3"
        )
        val user7 = Users(
            name = "user6",
            userName = "userName6",
            followersCount = 20,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = ""
        )
        val user8 = Users(
            name = "user7",
            userName = "userName7",
            followersCount = 50,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = "murali4"
        )
        val user9 = Users(
            name = "user8",
            userName = "userName8",
            followersCount = 30,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = ""
        )
        val user10 = Users(
            name = "user9",
            userName = "userName9",
            followersCount = 70,
            email = "email",
            bioInfo = "bio1",
            createdAt = Date().time,
            imageName = "murali5"
        )
        val userList = listOf(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)

        viewModelScope.launch(Dispatchers.IO) {
            val insertUser = userRepo.insertUserList(userList)
            _insertUser.postValue(insertUser)
        }
    }

    private val _getUser = MutableLiveData<Users>()
    val getUser: LiveData<Users>
        get() = _getUser

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val getUser = userRepo.getUser()
            getUser.collect {
                _getUser.postValue(it)
            }
        }
    }

    private val _getUserList = MutableLiveData<MutableList<Users>>()
    val getUserList: LiveData<MutableList<Users>>
        get() = _getUserList

    fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            val getUserList = userRepo.getUserList()
            getUserList.collect {
                _getUserList.postValue(it)
            }
        }
    }
}