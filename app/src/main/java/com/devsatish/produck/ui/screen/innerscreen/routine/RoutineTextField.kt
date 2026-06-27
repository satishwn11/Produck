package com.devsatish.produck.ui.screen.innerscreen.routine

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun RoutineTextField(
    title: String,
    onValueChange: (String) -> Unit,
    text : String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(value = title, onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 20.sp),
        placeholder = { Text( text, fontSize = 20.sp) },
        modifier = modifier
    )
}