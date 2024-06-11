package com.muralidhar.threadsapp.bottom_bar

import com.muralidhar.threadsapp.R


sealed class BottomBarItems(
    val route: String,
    val icon: Int
) {

    object Home : BottomBarItems(
        "home",
        R.drawable.home
    )

    object Search : BottomBarItems(
        "search",
        R.drawable.search
    )

    object Post : BottomBarItems(
        "post",
        R.drawable.post
    )

    object Notification : BottomBarItems(
        "notification",
        R.drawable.love
    )

    object Profile : BottomBarItems(
        "profile",
        R.drawable.person
    )

}