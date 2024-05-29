package com.example.alarmapp.mainscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alarmapp.Routes
import com.example.alarmapp.addalarm.AddUnitAlarm
import com.example.alarmapp.settingscreen.SettingScreen

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) {context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner = staticCompositionLocalOf<ViewModelStoreOwner> {
    error("Undefined")
}

@Composable
fun MainNaviGraph(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider (
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = Routes.MainScreen.route){
            composable(Routes.MainScreen.route) {
                MainScreen(navController)
            }

            composable(Routes.Setting.route){
                SettingScreen()
            }

            composable(Routes.AddUnitAlarm.route){
                AddUnitAlarm()
            }
            
            // 추가적인 화면 등등
        }
    }
}