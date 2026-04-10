package com.devsatish.produck.ui.screen.tabscreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devsatish.produck.R
import com.devsatish.produck.ui.theme.darkBlue
import com.devsatish.produck.ui.theme.secondColor
import com.devsatish.produck.ui.theme.themeColor
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(timerViewModel: TimerViewModel) {

    val inter = FontFamily(Font(R.font.inter_medium))
    val titleGreet = FontFamily(Font(R.font.fellgreet))
    val titleGreet2 = FontFamily(Font(R.font.jacquesfrancoisregular))

    var selectedMinutes by remember { mutableIntStateOf(25) }

    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var taskName by remember { mutableStateOf("") }

    val context = LocalContext.current

    val completedList by timerViewModel.completedTasks.collectAsState()
    val tasklist by timerViewModel.popularTaskTitles.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Produck",
                        fontSize = 32.sp,
                        fontFamily = titleGreet
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColor,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = secondColor,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
    ) { paddingValues ->

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(Color(0xFFE0E0E0))
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(8.dp)
                            .background(color = Color(0xFFE0E0E0))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "New Timer",
                            fontSize = 24.sp,
                            color = darkBlue,
                            fontFamily = inter
                        )

                        Box {
                            OutlinedTextField(
                                value = taskName,
                                onValueChange = { taskName = it },
                                singleLine = true,
                                label = { Text("Enter title..") }
                            )

                            IconButton(
                                onClick = {
                                    expanded = true
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_down),
                                    contentDescription = null
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            tasklist.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        taskName = it
                                        expanded = false
                                    }
                                )
                            }
                        }


                        Box {
                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .width(250.dp)
                                    .height(40.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(20.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Default - $selectedMinutes Minutes",
                                    fontFamily = inter
                                )
                            }

                            IconButton(
                                onClick = {
                                    expanded2 = true
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.arrow_down),
                                    contentDescription = null
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = expanded2,
                            onDismissRequest = { expanded2 = false }
                        ) {
                            listOf(2, 5, 10, 15, 20, 25, 30, 40, 60, 120).forEach {
                                DropdownMenuItem(
                                    text = { Text("$it minutes") },
                                    onClick = {
                                        selectedMinutes = it
                                        expanded2 = false
                                    }
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        Box(
                            modifier = Modifier
                                .width(160.dp)
                                .height(50.dp)
                                .shadow(elevation = 8.dp)
                                .background(
                                    color = secondColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (taskName.isNotBlank()) {
                                        timerViewModel.startTimer(
                                            taskName,
                                            selectedMinutes
                                        )

                                        Toast.makeText(
                                            context, "Timer Started",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context, "Enter task",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Start Timer",
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        }
                    }

                    IconButton(
                        onClick = { showDialog = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(40.dp)
                            .background(color = Color(0xFFD9D9D9))
                            .shadow(elevation = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.Gray

                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .shadow(4.dp, RoundedCornerShape(18.dp))
                    .background(Color.White, RoundedCornerShape(18.dp))
                    .border(
                        1.dp, Color(0xFFE0E0E0),
                        RoundedCornerShape(18.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = timerViewModel.formattedTime(),
                    fontSize = 48.sp,
                    fontFamily = inter
                )
            }

            val groupedList = completedList.groupBy { it.completedDate }
            LazyColumn {
                groupedList.forEach { (date, tasks) ->
                    val totalMinutes = tasks.sumOf { it.durationMinutes }
                    val hours = totalMinutes / 60
                    val minutes = totalMinutes % 60

                    item {
                        Text(
                            text = date,
                            fontSize = 30.sp,
                            fontFamily = titleGreet2,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    items(tasks) { task ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 12.dp)
                                .background(
                                    Color(0xFFF5F5F5),
                                    RoundedCornerShape(12.dp)
                                )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .shadow(
                                        8.dp,
                                        RoundedCornerShape(12.dp), clip = false
                                    )
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = timerViewModel.capitalizeFirst(task.title),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = inter,
                                    color = Color(0xFF050E6D)
                                )

                                Spacer(Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = "Duration -",
                                            fontSize = 20.sp,
                                            fontFamily = inter,
                                            color = Color(0xFF3841A4)
                                        )

                                        Spacer(Modifier.width(6.dp))

                                        Text(
                                            text = " ${task.durationMinutes} min",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = inter,
                                            color = Color(0xFF3841A4)
                                        )
                                    }
                                    Text(
                                        text = task.completedTime,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = inter,
                                        color = Color(0xFF4C4E67)
                                    )

                                }

                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 12.dp)
                                .background(
                                    Color(0xFFF5F5F5),
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .shadow(
                                        8.dp,
                                        RoundedCornerShape(12.dp), clip = false
                                    )
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 18.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total     -",
                                    fontSize = 20.sp,
                                    fontFamily = inter,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Red
                                )

                                Text(
                                    text = "        $hours hr $minutes min",
                                    fontSize = 20.sp,
                                    fontFamily = inter,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Red
                                )
                            }

                            Spacer(Modifier.height(8.dp))
                        }
                    }

                }
            }
        }

    }
}