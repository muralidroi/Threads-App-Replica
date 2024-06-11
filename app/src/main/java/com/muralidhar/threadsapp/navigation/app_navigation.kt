package com.muralidhar.threadsapp.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muralidhar.threadsapp.bottom_bar.BottomBarItems
import com.muralidhar.threadsapp.features.screens.HomeScreen
import com.muralidhar.threadsapp.features.screens.NotificationScreen
import com.muralidhar.threadsapp.features.screens.PostScreen
import com.muralidhar.threadsapp.features.screens.ProfileScreen
import com.muralidhar.threadsapp.features.screens.SearchScreen
import com.muralidhar.threadsapp.viewmodel.UserViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(
    navHostController: NavHostController, userViewModel: UserViewModel, modifier: Modifier
) {

    LaunchedEffect (key1 = Unit) {
        userViewModel.getUser()
        userViewModel.getUserList()
    }

    val user = userViewModel.getUser.observeAsState()

    val userList = userViewModel.getUserList.observeAsState()

    NavHost(navController = navHostController, startDestination = BottomBarItems.Home.route) {
        composable(BottomBarItems.Home.route) {
            userList.value.let {
                it?.let { it1 -> HomeScreen(modifier, it1) }
            }
        }
        composable(BottomBarItems.Search.route) {
            userList.value.let {
                it?.let { it1 -> SearchScreen(it1) }
            }
        }
        composable(BottomBarItems.Post.route) {
            PostScreen()
        }
        composable(BottomBarItems.Notification.route) {
            userList.value.let {
                it?.let { it1 -> NotificationScreen(it1,modifier) }
            }
        }
        composable(BottomBarItems.Profile.route) {
            ProfileScreen(user, modifier)
        }
    }

}