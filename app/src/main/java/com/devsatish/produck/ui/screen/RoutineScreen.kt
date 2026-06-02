package com.devsatish.produck.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsatish.produck.data.model.routine.RoutineEntity
import com.devsatish.produck.ui.viewmodel.RoutineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    viewModel: RoutineViewModel
) {

    val routineList by viewModel.allRoutine
        .collectAsStateWithLifecycle(initialValue = emptyList())

    var id by remember { mutableIntStateOf(0) }
    var title by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    var show by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Routine")
                },
                actions = {
                    IconButton(onClick = {
                        show = !show
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (show) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = startTime,
                        onValueChange = {
                            startTime = it
                        },
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        placeholder = {
                            Text(
                                "Start Timer",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    OutlinedTextField(
                        value = endTime,
                        onValueChange = {
                            endTime = it
                        },
                        textStyle = TextStyle(
                            fontSize = 20.sp
                        ),
                        placeholder = {
                            Text(
                                "End Time",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text("Task")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (id == 0) {
                    Button(
                        onClick = {
                            focusManager.clearFocus()

                            viewModel.addRoutine(
                                startTime = startTime,
                                endTime = endTime,
                                title = title
                            )

                            title = ""
                        }
                    ) {
                        Text("Save Routine")
                    }
                } else {
                    Button(onClick = {
                        focusManager.clearFocus()

                        viewModel.updateItem(
                            RoutineEntity(
                                id = id,
                                startTime = startTime,
                                endTime = endTime,
                                title = title
                            )
                        )

                        title = ""
                        id = 0
                    }) {
                        Text("Update Item")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            Divider(thickness = 1.dp)
            Text(
                text = "Goal - Clear the CHSL Exam",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Divider(thickness = 1.dp)

            LazyColumn {

                items(routineList) { routine ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .combinedClickable(

                                onDoubleClick = {
                                    viewModel.deleteItem(routine)
                                },
//                                    onLongClick = {  },
                                onClick = {
                                    id = routine.id
                                    startTime = routine.startTime
                                    endTime = routine.endTime
                                    title = routine.title

                                    show = true
                                }

                            ),
                        elevation = CardDefaults.cardElevation(6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {

                            // Left side → Time
                            Text(
                                text = "${routine.startTime} - ${routine.endTime}",
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp
                            )

                            // Right side → Task
                            Text(
                                text = routine.title,
                                modifier = Modifier.weight(1f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


//
//@Composable
//fun RoutineScreen() {
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(12.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Text(
//            text = "Goal - Clear the CHSL Exam",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            fontFamily = FontFamily.Serif,
//            modifier = Modifier.fillMaxWidth(),
//            textAlign = TextAlign.Center
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        Divider(thickness = 1.dp)
//
//        Spacer(Modifier.height(6.dp))
//
//        Text(
//            text = "Routines",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//        Spacer(Modifier.height(8.dp))
//
//        routineList.forEach { it ->
//            RoutineItemView(it)
//        }
//
//    }
//}
//
//@Composable
//fun RoutineItemView(item: Pair<String, String>) {
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp),
//        elevation = CardDefaults.cardElevation(6.dp),
//        shape = RoundedCornerShape(12.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//
//            // Left side → Time
//            Text(
//                text = item.first,
//                modifier = Modifier.weight(1f),
//                fontSize = 14.sp
//            )
//
//            // Right side → Task
//            Text(
//                text = item.second,
//                modifier = Modifier.weight(1f),
//                fontSize = 16.sp
//            )
//        }
//    }
//}