package com.devsatish.produck.utils.navigation

import com.devsatish.produck.R

sealed class BottomNavItems (
    val title: String,
    val route: String,
    val icon: Int
) {
    object Home: BottomNavItems("Home","home", R.drawable.home_icon)
    object Timer: BottomNavItems("Timer", "timer", R.drawable.timer_icon)
    object Note : BottomNavItems("Note","note",R.drawable.trophy)
}