package com.example.alarmapp

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.model.rememberAlarmState
import com.example.alarmapp.screen.MainScreen
import com.example.alarmapp.screen.SettingScreen
import com.example.alarmapp.screen.UpdateAlarmScreen

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNaviGraph(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()
    val mainViewModel: MainViewModel =
        viewModel(factory = MainViewModel.Factory(LocalContext.current))

    NavHost(navController = navController, startDestination = Routes.MainScreen.route) {
        composable(
            route = Routes.MainScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "myapp://main_screen"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            MainScreen(navController, mainViewModel)
        }

        composable(route = Routes.Setting.route) {
            SettingScreen()
        }

        composable(
            route = Routes.CreateAlarm.route
        ) {
            UpdateAlarmScreen(navController, mainViewModel, rememberAlarmState())
        }

        composable(
            route = Routes.UpdateAlarm.route,
            arguments = listOf(navArgument("alarmId") { type = NavType.IntType })
        ) {
            val alarmId = it.arguments?.getInt("alarmId")
            UpdateAlarmScreen(navController, mainViewModel, mainViewModel.alarmStateMap[alarmId]!!)
        }

        composable(
            route = Routes.CreateAlarmInGroup.route,
            arguments = listOf(navArgument("groupName") { type = NavType.StringType })
        ) {
            val groupName = it.arguments?.getString("groupName")
            val alarm = rememberAlarmState()
            alarm.groupName = groupName!!
            UpdateAlarmScreen(navController, mainViewModel, alarm)
        }

        // 추가적인 화면 등등
    }
}