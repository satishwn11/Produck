package com.devsatish.produck.ui.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueInput(
    navController: NavHostController,
    timerViewModel: TimerViewModel
) {

    val tasklist by timerViewModel.popularTaskTitles.collectAsState()
    val focusManager = LocalFocusManager.current

    var expand by remember { mutableStateOf(false) }
    var issueCategory by remember { mutableStateOf("select category") }
    var issueTitle by remember { mutableStateOf("") }
    var issueDescription by remember { mutableStateOf("") }
    val context = LocalContext.current

//    val today = LocalDate.now()
//    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
//    val formattedDate = today.format(formatter)

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable {
                    expand = true
                }
        ) {
            Text(
                text = issueCategory,
                fontSize = 16.sp,
                fontWeight = FontWeight.W800,
                color = Color.Blue
            )
        }

        BasicTextField(
            value = issueTitle,
            onValueChange = { issueTitle = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textStyle = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.DarkGray
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.Blue),
            decorationBox = { innerTextField ->

                if (issueTitle.isEmpty()) {
                    Text(
                        text = "Enter title...",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.DarkGray
                    )
                }
                innerTextField()
            }
        )

        BasicTextField(
            value = issueDescription,
            onValueChange = { issueDescription = it },
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 150.dp),
            textStyle = TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color.Black
            ),
            maxLines = 6,
            decorationBox = { innerTextField ->
                if (issueDescription.isEmpty()) {
                    Text(
                        text = "Write in detail...",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )

        Button(onClick = {

            if(issueTitle.isNotEmpty() && issueDescription.isNotEmpty() && issueCategory != "select category") {
                timerViewModel.insertIssue(
                    category = issueCategory,
                    title = issueTitle,
                    description = issueDescription
                )
                focusManager.clearFocus()
                navController.navigateUp()
            } else {
                Toast.makeText(context,"Please fill all 3 section",
                    Toast.LENGTH_SHORT).show()
            }

        }) { Text("Save Issue") }

        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false }
        ) {
            tasklist.forEach { task ->
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
                                text = task,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W500,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    },
                    onClick = {
                        issueCategory = task
                        expand = false
                    }
                )
            }
        }
    }
}