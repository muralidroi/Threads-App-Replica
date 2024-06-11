package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.muralidhar.threadsapp.room.entities.Users


@ExperimentalFoundationApi
@Composable
fun ProfileScreen(user: State<Users?>, modifier: Modifier) {
    val firstUser = user.value
    ProfileScreenTopBar(modifier)

    firstUser.let {
        if (it != null) {
            ProfileDescriptionLayout(user = it, modifier)
        }
    }

    ProfileEditButton(modifier)

    ProfileThreadTabSection(modifier)
}