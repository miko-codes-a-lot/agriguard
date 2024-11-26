package com.example.agriguard.modules.main.menu.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class NavItem(
    val icon: Painter,
    val routeName: String,
    val navigation: NavController,
)