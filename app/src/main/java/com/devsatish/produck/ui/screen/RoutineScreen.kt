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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoutineScreen() {

    val routineList = listOf(
        RoutineItem("6:00 - 7:00", "Kotlin"),
        RoutineItem("7:00 - 8:00", "Android app development"),
        RoutineItem("9:00 - 11:00", "Reasoning"),
        RoutineItem("11:00 - 12:30", "GS"),
        RoutineItem("1:00 - 2:00", "Academic Study"),
        RoutineItem("6:00 - 9:00", "Mathematics"),
        RoutineItem("9:00 - 10:00","GK")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Routines",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))

        routineList.forEach {
            RoutineItemView(it)
        }
    }
}

data class RoutineItem(
    val time: String,
    val task: String
)

@Composable
fun RoutineItemView(item: RoutineItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            // Left side → Time
            Text(
                text = item.time,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )

            // Right side → Task
            Text(
                text = item.task,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
        }
    }
}