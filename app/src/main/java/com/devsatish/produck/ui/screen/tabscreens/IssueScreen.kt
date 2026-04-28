package com.devsatish.produck.ui.screen.tabscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devsatish.produck.R
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueScreen(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {
    val font1 = FontFamily(Font(R.font.jacquesfrancois_regular))
    val issues by timerViewModel.issues.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF960000),
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = "Feedbacks - Issues",
                        fontSize = 32.sp,
                        fontFamily = font1
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("issueInput")
                },
                containerColor = Color(0xFFC02F61)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    ) { paddingValues ->

        if (issues.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No achievements yet...")
            }

        } else {

            val groupedList = issues.groupBy {
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    .format(Date(it.createdAt))
            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                groupedList.forEach { (date, issues) ->

                    item {
                        Text(
                            text = date,
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    items(issues) { issue ->

                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(
                                    elevation = 6.dp,
                                    shape = RoundedCornerShape(12.dp),
                                    ambientColor = Color(0xFF99234C),
                                    spotColor = Color(0xFFA23434),
                                    clip = false
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Red,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onDoubleTap = {
                                            timerViewModel.deleteIssue(issue)
                                        }
                                    )
                                }
                        ) {

                            // Category
                            Text(
                                text = issue.category,
                                color = Color(0xFF293CA7),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600
                            )

                            // Title
                            Text(
                                text = issue.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )

                            // Description
                            Text(
                                text = issue.description,
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                .format(Date(issue.createdAt))

                            Text(
                                text = time,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}