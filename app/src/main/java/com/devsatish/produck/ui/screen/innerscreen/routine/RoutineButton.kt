package com.devsatish.produck.ui.screen.innerscreen.routine

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RoutineButton(
    onClick: () -> Unit,
    title: String
) {
    Button(
        onClick = onClick
    ) {
        Text(title)
    }
}