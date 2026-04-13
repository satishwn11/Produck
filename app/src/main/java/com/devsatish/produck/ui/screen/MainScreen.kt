package com.devsatish.produck.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.ui.screen.tabscreens.HomeScreen
import com.devsatish.produck.ui.screen.tabscreens.IssueScreen
import com.devsatish.produck.ui.screen.tabscreens.TimerScreen
import com.devsatish.produck.ui.screen.tabscreens.WinsScreen
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import com.devsatish.produck.utils.navigation.BottomNavItems

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, timerViewModel1: TimerViewModel) {

    val tabController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(tabController)
        }

    ) { paddingValues ->

        NavHost(
            navController = tabController,
            startDestination = BottomNavItems.Home.route,
            modifier = Modifier.padding(paddingValues)
            ) {
            composable(BottomNavItems.Home.route) {
                HomeScreen(timerViewModel1)
            }
            composable(BottomNavItems.Timer.route) {
                TimerScreen(tabController, timerViewModel1)
            }
            composable(BottomNavItems.Wins.route) {
                WinsScreen(navController, timerViewModel1)
            }
            composable(BottomNavItems.Issue.route) {
                IssueScreen(navController, timerViewModel1)
            }
        }
    }
}