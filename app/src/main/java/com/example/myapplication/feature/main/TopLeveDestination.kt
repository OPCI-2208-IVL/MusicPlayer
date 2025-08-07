package com.example.myapplication.feature.main

import android.graphics.drawable.Icon
import com.example.myapplication.R
import com.example.myapplication.feature.discovery.DISCOVER_ROUTE
import com.example.myapplication.feature.my.MY_ROUTE
import com.example.myapplication.feature.settings.SETTING_ROUTE

enum class TopLeveDestination(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val titleTextID: Int,
    val route: String
) {
    DISCOVER(
        selectedIcon = R.drawable.dicover_icon_selected,
        unSelectedIcon = R.drawable.discover_icon_unselected,
        titleTextID = R.string.discover,
        route = DISCOVER_ROUTE
    ),

    MY(
        selectedIcon = R.drawable.my_icon_selected,
        unSelectedIcon = R.drawable.my_icon_unselected,
        titleTextID = R.string.my,
        route = MY_ROUTE
    ),

    SETTING(
        selectedIcon = R.drawable.setting_icon_selected,
        unSelectedIcon = R.drawable.setting_icon_unselected,
        titleTextID = R.string.setting,
        route = SETTING_ROUTE
    )
}
