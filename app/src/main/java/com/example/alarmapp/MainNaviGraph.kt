package com.example.alarmapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

val LocalNavGraphViewModelStoreOwner = staticCompositionLocalOf<ViewModelStoreOwner> {
    error("Undefined")
}

@Composable
fun MainNaviGraph(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()
    val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.MainScreen.route) {
            composable(Routes.MainScreen.route) {
                MainScreen(navController, mainViewModel)
            }

            composable(Routes.Setting.route) {
                SettingScreen()
            }

            composable(
                route = Routes.CreateAlarm.route + "?alarmId={alarmId}",
                arguments = listOf(navArgument("alarmId") { defaultValue = -1 })
            ) {
                val target = it.arguments?.getInt("alarmId")
                val targetAlarmState = if (target==-1) {
                    rememberAlarmState()
                } else{
                    mainViewModel.alarmStateMap[target] ?: rememberAlarmState()
                }
                UpdateAlarmScreen(navController, mainViewModel, targetAlarmState)
            }
            // 추가적인 화면 등등
        }
    }
}