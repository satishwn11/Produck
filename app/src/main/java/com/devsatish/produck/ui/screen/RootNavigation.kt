package com.devsatish.produck.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.ui.screen.innerscreen.issuescreen.IssueInput
import com.devsatish.produck.ui.screen.innerscreen.routine.RoutineScreen
import com.devsatish.produck.ui.screen.innerscreen.winsscreen.WinsInput
import com.devsatish.produck.ui.viewmodel.routineviewmodel.RoutineViewModel
import com.devsatish.produck.ui.viewmodel.timerviewmodel.TimerViewModel
import com.devsatish.produck.utils.navigation.Routes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavigation(
    timerViewModel: TimerViewModel,
    routineViewModel: RoutineViewModel,
    screen: String?,
    onNavigateConsumed: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN
    ) {
        composable(Routes.MAIN) {
            MainScreen(navController, timerViewModel, screen, onNavigateConsumed)
        }

        // Inner Screens
        composable(Routes.WIN_INPUT) {
            WinsInput(navController, timerViewModel)
        }
        composable(Routes.ISSUE_INPUT) {
            IssueInput(navController, timerViewModel, routineViewModel)
        }
        composable(Routes.ROUTINE) {
            RoutineScreen(routineViewModel)
        }
    }

}
