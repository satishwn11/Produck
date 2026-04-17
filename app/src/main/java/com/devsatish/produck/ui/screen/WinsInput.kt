package com.devsatish.produck.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WinsInput(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {

    val completedList by timerViewModel.completedTasks.collectAsState()
    val focusManager = LocalFocusManager.current

    var expand by remember { mutableStateOf(false) }
    var win by remember { mutableStateOf("select category") }
    var winTitle by remember { mutableStateOf("") }
    var winDescription by remember { mutableStateOf("") }
    val context = LocalContext.current

    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    val formattedDate = today.format(formatter)

    val todayWins = completedList.filter { it.completedDate == formattedDate }

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .clickable { expand = true }
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Text(
                text = win,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.padding(14.dp)
            ) {

                BasicTextField(
                    value = winTitle,
                    onValueChange = { winTitle = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Yellow),
                    decorationBox = { innerTextField ->

                        Box {
                            if (winTitle.isEmpty()) {
                                Text(
                                    text = "Enter title...",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {

                BasicTextField(
                    value = winDescription,
                    onValueChange = { winDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 150.dp),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        color = Color.DarkGray
                    ),
                    maxLines = 6,
                    cursorBrush = SolidColor(Color(0xFF1565C0)),
                    decorationBox = { innerTextField ->

                        Box {
                            if (winDescription.isEmpty()) {
                                Text(
                                    text = "Write in detail...",
                                    fontSize = 18.sp,
                                    color = Color.DarkGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }

        Button(onClick = {

            if(winTitle.isNotEmpty() && winDescription.isNotEmpty() && win != "select category") {
                timerViewModel.insertWin(
                    category = win,
                    title = winTitle,
                    description = winDescription
                )
                focusManager.clearFocus()
                navController.navigateUp()
            } else {
                Toast.makeText(context,"Please fill all 3 section",
                    Toast.LENGTH_SHORT).show()
            }

        },
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 1.dp
            )
            ) { Text("Save achievement") }

        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false }
        ) {
            todayWins.forEach { task ->
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF1D8316),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Text(
                                text = task.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    },
                    onClick = {
                        win = task.title
                        expand = false
                    }
                )
            }
        }
    }
}