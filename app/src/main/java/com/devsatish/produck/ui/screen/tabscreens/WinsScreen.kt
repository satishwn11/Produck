package com.devsatish.produck.ui.screen.tabscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.devsatish.produck.data.model.wins.WinEntity
import com.devsatish.produck.ui.screen.components.DeleteAlertDialog
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinsScreen(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {

    val font1 = FontFamily(Font(R.font.jacquesfrancois_regular))
    val wins by timerViewModel.wins.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var selectedWin by remember { mutableStateOf<WinEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00820E),
                    titleContentColor = Color.White
                ),
                title = {
                    Text(
                        text = "Achievements",
                        fontSize = 32.sp,
                        fontFamily = font1
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("winInput")
                },
                containerColor = Color(0xFF3B9538)
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

        // alert dialog
        DeleteAlertDialog(
            showDialog = showDialog,
            title = selectedWin?.title ?: "",
            onCancel = {
                selectedWin = null
                showDialog = false
            },
            onDelete = {
                selectedWin?.let {
                    timerViewModel.deleteWin(it)
                }
                selectedWin = null
                showDialog = false
            }
        )

        if (wins.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No achievements yet...")
            }

        } else {
            val todayDate = SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(Date())

            val groupedList = wins.groupBy {
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    .format(Date(it.createdAt))
            }

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {

                // If today has no data at all
                if (!groupedList.containsKey(todayDate)) {
                    item {
                        Text(
                            text = "Today",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        Text(
                            text = "No items listed today",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }

                groupedList.forEach { (date, wins) ->

                    item {
                        Text(
                            text = if (date == todayDate) "Today" else date,
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                        // existing win card code
                        items(
                            items = wins,
                            key = { it.id }
                        ) { win ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 6.dp, vertical = 4.dp)
                                    .shadow(
                                        elevation = 6.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        ambientColor = Color(0xFF006400),
                                        spotColor = Color(0xFF006400),
                                        clip = false
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Green,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .background(Color.White, RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onDoubleTap = {
                                                selectedWin = win
                                                showDialog = true
                                            }
                                        )
                                    }
                            ) {

                                // Category
                                Text(
                                    text = win.category,
                                    color = Color(0xFF293CA7),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W600
                                )

                                // Title
                                Text(
                                    text = win.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3B9538)
                                )

                                // Description
                                Text(
                                    text = win.description,
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                val time = SimpleDateFormat("hh:mm a", Locale.getDefault())
                                    .format(Date(win.createdAt))

                                Text(
                                    text = time,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }

                }
            }
        }
    }
}