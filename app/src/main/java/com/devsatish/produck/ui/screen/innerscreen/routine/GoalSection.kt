package com.devsatish.produck.ui.screen.innerscreen.routine

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GoalSection(
    goal: String,
    onDoubleClick: () -> Unit,
    showEditor: Boolean,
    goalText: String,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit
) {
    HorizontalDivider(thickness = 1.dp)
    Text(
        text = goal,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .combinedClickable(
                onClick = {},
                onDoubleClick = {
                    onDoubleClick()
                }
            )
    )

    if (showEditor) {
        TextField(
            value = goalText,
            onValueChange = onValueChange,
            placeholder = {
                Text("Write..")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onSave()
            },
            modifier = Modifier.padding(3.dp)
        ) {
            Text("Save Goal")
        }
    }
    HorizontalDivider(thickness = 1.dp)

}