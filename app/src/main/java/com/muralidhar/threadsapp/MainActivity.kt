package com.muralidhar.threadsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.muralidhar.threadsapp.bottom_bar.BottomBar
import com.muralidhar.threadsapp.navigation.AppNavigation
import com.muralidhar.threadsapp.ui.theme.ThreadsAppTheme
import com.muralidhar.threadsapp.utils.AppKeysStore
import com.muralidhar.threadsapp.viewmodel.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val usersViewModel: UserViewModel by viewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            AppKeysStore.isAlreadyInstalled(applicationContext)
                .collectLatest { isAlreadyInstalled ->
                    if (!isAlreadyInstalled) {
                        usersViewModel.insertUser()
                        AppKeysStore.setFirstTimeInstall(isInstall = true, applicationContext)
                        usersViewModel.insertUser
                    }
                }
        }

        setContent {
            val navHostController = rememberNavController()
            ThreadsAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.height(60.dp),
                            containerColor = Color.White,
                            contentPadding = PaddingValues(horizontal = 20.dp),
                        ) {
                            BottomBar(navHostController = navHostController)
                        }
                    }
                ) {
                    AppNavigation(navHostController, usersViewModel, Modifier)
                }
            }
        }
    }
}
