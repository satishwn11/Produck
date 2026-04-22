package com.devsatish.produck.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(timerViewModel: TimerViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            MainScreen(navController, timerViewModel)
        }
        composable("winInput") {
            WinsInput(navController, timerViewModel)
        }
        composable("issueInput") {
            IssueInput(navController, timerViewModel)
        }
        composable("routineScreen") {
            RoutineScreen()
        }
    }

}