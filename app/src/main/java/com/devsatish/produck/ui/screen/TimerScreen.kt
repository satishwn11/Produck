package com.devsatish.produck.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devsatish.produck.R
import com.devsatish.produck.ui.theme.secondColor
import com.devsatish.produck.ui.viewmodel.TimerViewModel

@Composable
fun TimerScreen(
    navController: NavController,
    timerViewModel: TimerViewModel
) {
    val inter = FontFamily(Font(R.font.inter_medium))

    val title = timerViewModel.currentTitle

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .shadow(4.dp, RoundedCornerShape(18.dp))
                .background(Color.White, RoundedCornerShape(18.dp))
                .border(1.dp, Color(0xFF26008D),
                    RoundedCornerShape(18.dp))
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title.ifBlank { "No Timer Running" },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 38.sp,
                fontFamily = inter,
                color = Color(0xFF221056)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = timerViewModel.formattedTime(),
            fontSize = 48.sp,
            color = Color(0xFF001566),
            fontFamily = inter
        )

        Spacer(Modifier.weight(0.7f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = Color(0xFF00BB00),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        timerViewModel.pause()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Pause", fontSize = 24.sp, color = Color.White)
            }

            Spacer(Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .padding(start = 80.dp)
                    .width(220.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = Color(0xFF001A51),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        timerViewModel.resume()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Resume", fontSize = 24.sp, color = Color.White)
            }
        }

        Spacer(Modifier.weight(1.4f))

        Box(
            modifier = Modifier
                .width(240.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(
                    color = secondColor,
                    shape = RoundedCornerShape(15.dp)
                )
                .clickable {
                    if (timerViewModel.currentTitle.trim().isNotEmpty()
                        && timerViewModel.elapsedSeconds > 60) {
                        timerViewModel.stopAndSave()
                        navController.popBackStack()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Complete", fontSize = 24.sp, color = Color.White)
        }

        Spacer(Modifier.height(40.dp))

    }
}