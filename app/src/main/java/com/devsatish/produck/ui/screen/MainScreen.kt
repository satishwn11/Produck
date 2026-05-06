package com.devsatish.produck.ui.screen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsatish.produck.navigation.Routes
import com.devsatish.produck.ui.screen.tabscreens.HomeScreen
import com.devsatish.produck.ui.screen.tabscreens.IssueScreen
import com.devsatish.produck.ui.screen.tabscreens.TimerScreen
import com.devsatish.produck.ui.screen.tabscreens.WinsScreen
import com.devsatish.produck.ui.theme.Inter
import com.devsatish.produck.ui.theme.themeColor
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import com.devsatish.produck.utils.navigation.BottomNavItems
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, timerViewModel1: TimerViewModel) {

    val tabController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(color = themeColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Produck",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(2.dp)
                            .background(color = Color.DarkGray)
                    )

                    Spacer(Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                            .shadow(
                                elevation = 4.dp,
                                shape = RectangleShape
                            )
                            .clip(RectangleShape)
                            .background(Color.White)
                            .border(
                                width = 0.5.dp,
                                color = Black,
                                shape = RectangleShape
                            )
                            .clickable {
                                navController.navigate(Routes.ROUTINE)
                            }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Routine",
                            fontSize = 24.sp,
                            fontFamily = Inter,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    ) {
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
}