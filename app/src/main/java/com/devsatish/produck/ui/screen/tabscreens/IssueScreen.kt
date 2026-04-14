package com.devsatish.produck.ui.screen.tabscreens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devsatish.produck.ui.theme.darkYellow
import com.devsatish.produck.R
import com.devsatish.produck.ui.theme.issuedarkyellow
import com.devsatish.produck.ui.theme.windarkgreen
import com.devsatish.produck.ui.theme.wingreen
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
    val titleGreet2 = FontFamily(Font(R.font.jacquesfrancoisregular))
    val issues by timerViewModel.issues.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkYellow,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = "Feedbacks - Issues",
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("issueInput")
                },
                containerColor = darkYellow
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Black,
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

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(issues) { issue ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(issuedarkyellow, RoundedCornerShape(12.dp))
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
                            color = Color.Cyan,
                            fontSize = 14.sp
                        )

                        // Title
                        Text(
                            text = issue.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        // Description
                        Text(
                            text = issue.description,
                            fontSize = 16.sp,
                            color = Color.LightGray
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Date & Time
                        val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            .format(Date(issue.createdAt))

                        val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            .format(Date(issue.createdAt))

                        Text(
                            text = "$date • $time",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}