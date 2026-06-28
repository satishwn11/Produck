package com.devsatish.produck.ui.screen.innerscreen.routine

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devsatish.produck.data.model.routine.RoutineEntity

@Composable
fun RoutineItemCard(
    routine: RoutineEntity,
    onDoubleClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .combinedClickable(

                onDoubleClick = onDoubleClick,
                onLongClick = onLongClick,
                onClick = {}
            ),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF5F74D5),
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            // Left side → Time
            Text(
                text = "${routine.startTime} - ${routine.endTime}",
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Right side → Task
            Text(
                text = routine.title,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        }
    }
}