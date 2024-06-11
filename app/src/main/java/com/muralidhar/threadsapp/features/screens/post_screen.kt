package com.muralidhar.threadsapp.features.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun PostScreen() {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            //userViewModel.insertUser(Users(1,"John","mail.com","john mailbox",""))
        }
    }
}