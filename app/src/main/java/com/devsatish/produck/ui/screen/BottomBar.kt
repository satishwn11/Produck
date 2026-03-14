package com.devsatish.produck.ui.screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.devsatish.produck.utils.navigation.BottomNavItems

@Composable
fun BottomBar(tabNavController: NavController) {
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Timer
    )

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    tabNavController.navigate(item.route) {

                        popUpTo(tabNavController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painter = painterResource(item.icon),
                    contentDescription = null) },
                label = { Text(item.title) }
            )
        }
    }
}