package com.devsatish.produck.ui.screen.innerscreen.routine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsatish.produck.data.model.routine.RoutineEntity
import com.devsatish.produck.ui.screen.common.DeleteAlertDialog
import com.devsatish.produck.ui.viewmodel.routineviewmodel.RoutineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(viewModel: RoutineViewModel) {

    var showEditor by remember { mutableStateOf(false) }
    var showInput by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // data store variables
    var goalText by remember { mutableStateOf("") }

    val goal by viewModel.goal.collectAsStateWithLifecycle()


    // screen variables
    val routineList by viewModel.allRoutine
        .collectAsStateWithLifecycle(initialValue = emptyList())

    var id by remember { mutableIntStateOf(0) }
    var title by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<RoutineEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Routine",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { showInput = !showInput }) {
                        if (showInput) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .rotate(180f)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            if (showInput) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    RoutineTextField(
                        startTime, onValueChange = { startTime = it },
                        text = "Start Time", modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    RoutineTextField(
                        endTime, onValueChange = { endTime = it },
                        text = "End Time", modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                RoutineTextField(
                    title, onValueChange = { title = it },
                    text = "Task", modifier = Modifier.fillMaxWidth()
                )

                if (id == 0) {
                    RoutineButton(
                        onClick = {
                            focusManager.clearFocus()

                            viewModel.addRoutine(
                                startTime = startTime,
                                endTime = endTime,
                                title = title
                            )

                            title = ""
                        },
                        title = "Save Routine"
                    )
                } else {
                    RoutineButton(
                        onClick = {
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
                        },
                        title = "Update Item"
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Goal Section
            GoalSection(
                goal,
                onDoubleClick = { showEditor = !showEditor },
                showEditor,
                goalText,
                onValueChange = { goalText = it },
                onSave = { viewModel.saveGoal(goalText) },
            )

            Spacer(Modifier.height(7.dp))

            DeleteAlertDialog(
                showDialog = showDialog, title = selectedItem?.title ?: "",
                onCancel = {
                    selectedItem = null
                    showDialog = false
                },
                onDelete = {
                    selectedItem?.let { viewModel.deleteItem(it) }
                    selectedItem = null
                    showDialog = false
                }
            )

            LazyColumn {

                items(routineList) { routine ->

                    RoutineItemCard(
                        routine,
                        onDoubleClick = {
                            selectedItem = routine
                            showDialog = true
                        },
                        onLongClick = {
                            id = routine.id
                            startTime = routine.startTime
                            endTime = routine.endTime
                            title = routine.title

                            showInput = true
                        }
                    )
                }
            }
        }
    }
}