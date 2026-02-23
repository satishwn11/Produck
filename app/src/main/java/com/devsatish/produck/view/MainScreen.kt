package com.devsatish.produck.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.navigation.BottomNavItems
import com.devsatish.produck.viewmodel.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(timerViewModel1: TimerViewModel) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItems.Home.route,
            modifier = Modifier.padding(paddingValues)
            ) {
            composable(BottomNavItems.Home.route) {
                HomeScreen(navController,timerViewModel1)
            }
            composable(BottomNavItems.Timer.route) {
                TimerScreen(navController, timerViewModel1)
            }
        }
    }
}