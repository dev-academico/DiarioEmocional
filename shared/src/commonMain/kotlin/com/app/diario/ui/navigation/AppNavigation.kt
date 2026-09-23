package com.app.diario.ui.navigation

import PensamentoDisfuncionalRoute
import PontuacaoDiariaRoute
import RelatoIndividualRoute
import RelatoIndividualScreen
import RelatosRoute
import RelatosScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.diario.ui.mock.relatosMock
import HomeRoute
import com.app.diario.ui.screens.HomeScreen

@Composable
fun AppNavigation(
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ) {

        composable<HomeRoute> {

            HomeScreen(
                onRelatosClick = {
                    navController.navigate(RelatosRoute)
                },
                onPensamentosClick = {
                    navController.navigate(PensamentoDisfuncionalRoute)
                },
                onPontuacaoClick = {
                    navController.navigate(PontuacaoDiariaRoute)
                }
            )
        }

        composable<RelatosRoute> { backStackEntry ->

            val rota = backStackEntry.toRoute<RelatosRoute>()

            RelatosScreen(
                relatos = relatosMock,
                onRelatoClick = { relato ->
                    navController.navigate(
                        RelatoIndividualRoute(
                            dataRegistro = relato.dataRegistro
                        )
                    )
                },
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        composable<RelatoIndividualRoute> { backStackEntry ->

            val rota = backStackEntry.toRoute<RelatoIndividualRoute>()

            RelatoIndividualScreen(
                dataRegistro = rota.dataRegistro,
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        composable<PensamentoDisfuncionalRoute> {
            PensamentoDisfuncionalScreen(
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        composable<PontuacaoDiariaRoute> {
            PontuacaoDiariaScreen(
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun PensamentoDisfuncionalScreen(onVoltar: () -> Boolean) {
    TODO("Not yet implemented")
}

@Composable
fun PontuacaoDiariaScreen(onVoltar: () -> Boolean) {
    TODO("Not yet implemented")
}

