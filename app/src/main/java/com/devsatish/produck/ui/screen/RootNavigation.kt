package com.devsatish.produck.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.navigation.Routes
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(timerViewModel: TimerViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(navController, timerViewModel)
        }
        composable(Routes.WIN_INPUT) {
            WinsInput(navController, timerViewModel)
        }
        composable(Routes.ISSUE_INPUT) {
            IssueInput(navController, timerViewModel)
        }
        composable(Routes.ROUTINE) {
            RoutineScreen()
        }
    }

}