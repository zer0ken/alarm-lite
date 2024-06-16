package com.example.alarmapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.alarmapp.model.Filter
import com.example.alarmapp.model.MainViewModel
import com.example.alarmapp.model.rememberAlarmState
import com.example.alarmapp.screen.MainScreen
import com.example.alarmapp.screen.SettingScreen
import com.example.alarmapp.screen.UpdateAlarmScreen
import com.example.alarmapp.screen.filterscreens.GroupFilterLabel
import com.example.alarmapp.screen.filterscreens.RepeatFilterLabel
import com.example.alarmapplication.AddFilterSetScreen
import com.example.alarmapplication.FilterSetListScreen

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

@Composable
fun MainNaviGraph(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()
    val mainViewModel: MainViewModel =
        viewModel(factory = MainViewModel.Factory(LocalContext.current))

    NavHost(navController = navController, startDestination = Routes.MainScreen.route) {
        composable(route = Routes.MainScreen.route) {
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

        composable(Routes.FilterSetListScreen.route) {
            FilterSetListScreen(navController, mainViewModel)
        }

        composable(
            route = Routes.UpdateFilterSetScreen.route,
            arguments = listOf(navArgument("filterName") { type = NavType.StringType })
        ) {
            val filterName = it.arguments?.getString("filterName")
            val filter = mainViewModel.getFilterByName(filterName)!!
            AddFilterSetScreen(navController, mainViewModel, filter)
        }

        composable(Routes.AddFilterSetScreen.route) {
            AddFilterSetScreen(navController, mainViewModel)
        }
        composable(Routes.RepeatFilterLabel.route) {
            RepeatFilterLabel(navController, mainViewModel)
        }
        composable(Routes.GroupFilterLabel.route) {
            GroupFilterLabel(navController, mainViewModel)
        }
        // 추가적인 화면 등등
    }
}