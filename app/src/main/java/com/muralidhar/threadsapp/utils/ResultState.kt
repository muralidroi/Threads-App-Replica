package com.muralidhar.threadsapp.utils

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

sealed class ResultState<out T> {
    data class Success<out R>(val data:R) : ResultState<R>()
    data class Failure(val msg:Throwable) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}

@Composable
fun CommonDialog() {
    Dialog(onDismissRequest = { }) {
        CircularProgressIndicator()
    }
}