package com.app.diario.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Any,
)

val bottomNavItems = listOf(
    BottomNavItem("Relatos", Icons.Default.Edit, RelatosGraphRoute),
    BottomNavItem("Pensamentos", Icons.Default.Place, PensamentoGraphRoute),
    BottomNavItem("Avaliações", Icons.Default.Favorite, AvaliacaoGraphRoute),
    BottomNavItem("Sono", Icons.Default.Face, HigieneSonoGraphRoute),
)
