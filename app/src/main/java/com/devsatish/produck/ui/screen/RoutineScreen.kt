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

val routineList = listOf (
    "6:00 - 7:00" to "Kotlin",
    "7:00 - 8:00" to "Android app development",
    "9:00 - 11:00" to  "Reasoning",
    "11:00 - 12:30" to "GS",
    "1:00 - 2:00" to  "Academic Study",
    "6:00 - 9:00" to "Mathematics",
    "9:00 - 10:00" to "GK"
)

@Composable
fun RoutineScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Goal - Clear the CHSL Exam",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Divider(thickness = 1.dp)

        Spacer(Modifier.height(6.dp))

        Text(
            text = "Routines",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(Modifier.height(8.dp))

        routineList.forEach { it ->
            RoutineItemView(it)
        }

    }
}

@Composable
fun RoutineItemView(item: Pair<String, String>) {

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
                text = item.first,
                modifier = Modifier.weight(1f),
                fontSize = 14.sp
            )

            // Right side → Task
            Text(
                text = item.second,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
        }
    }
}