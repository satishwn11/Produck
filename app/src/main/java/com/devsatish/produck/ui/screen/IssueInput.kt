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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.devsatish.produck.ui.theme.darkBlue2
import com.devsatish.produck.ui.theme.darkPink
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueInput(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {
    val focusManager = LocalFocusManager.current

    var expand by remember { mutableStateOf(false) }
    var issueCategory by remember { mutableStateOf("select category") }
    var issueTitle by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }
    val context = LocalContext.current

    val newList = routineList + ("9:30 - 10:20" to "Expression")

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // category list
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .clickable { expand = true }
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Text(
                text = issueCategory,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
        }

        // title box
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
                modifier = Modifier.padding(12.dp)
            ) {

                BasicTextField(
                    value = issueTitle,
                    onValueChange = { issueTitle = it },
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
                            if (issueTitle.isEmpty()) {
                                Text(
                                    text = "Enter title...",
                                    fontSize = 28.sp,
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
                    value = issueDescription,
                    onValueChange = { issueDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp, max = 150.dp),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        color = Color.DarkGray
                    ),
                    maxLines = 6,
                    decorationBox = { innerTextField ->

                        Box {
                            if (issueDescription.isEmpty()) {
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

        Button(
            onClick = {

                if (
                    issueTitle.isNotEmpty()
                    && issueDescription.isNotEmpty()
                    && issueCategory != "select category"
                ) {
                    timerViewModel.insertIssue(
                        category = issueCategory,
                        title = issueTitle,
                        description = issueDescription
                    )
                    focusManager.clearFocus()
                    navController.navigateUp()
                } else {
                    Toast.makeText(
                        context, "Please fill all 3 section",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },

            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 1.dp
            )
        ) { Text("Save Issue") }

        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false }
        ) {
            newList.forEach { task ->
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier
                                .background(
                                    color =if(task.second == "Expression") darkPink else darkBlue2,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Text(
                                text = task.second,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    },
                    onClick = {
                        issueCategory = task.second
                        expand = false
                    }
                )
            }
        }
    }
}