package com.app.diario.ui.navigation

import CriarRelatoRoute
import CriarRelatoScreen
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
import PensamentoDisfuncionalIndividualRoute
import RegistroPensamentoIndividualScreen
import RegistroPensamentoScreen
import androidx.navigation.navDeepLink
import com.app.diario.ui.screens.HomeScreen
import pensamentosDisfuncionaisMock

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

        // HOME
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


        // LISTA DE RELATOS
        composable<RelatosRoute> {
            RelatosScreen(
                relatos = relatosMock,

                // CELULAR:
                // o clique navega para a página individual.
                //
                // LAYOUT LARGO:
                // o RelatosScreen intercepta esse clique
                // e apenas seleciona o relato.
                onRelatoIndividual = { relato ->
                    navController.navigate(
                        RelatoIndividualRoute(
                            dataRegistro = relato.dataRegistro
                        )
                    )
                },

                onNovoRelatoClick = {
                    navController.navigate(CriarRelatoRoute)
                },

                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


        // RELATO INDIVIDUAL
        composable<RelatoIndividualRoute>(
            deepLinks = listOf(
                navDeepLink<RelatoIndividualRoute>(
                    basePath = "diario://relato"
                )
            )
        ) { backStackEntry ->

            val rota = backStackEntry.toRoute<RelatoIndividualRoute>()

            RelatoIndividualScreen(
                dataRegistro = rota.dataRegistro,

                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


        // LISTA / FORMULÁRIO DE PENSAMENTO DISFUNCIONAL
        composable<PensamentoDisfuncionalRoute> {

            TODO()
        }


        // PENSAMENTOS DISFUNCIONAIS
        composable<PensamentoDisfuncionalRoute> {

            RegistroPensamentoScreen(
                pensamentos = pensamentosDisfuncionaisMock,

                onPensamentoClick = { pensamento ->
                    navController.navigate(
                        PensamentoDisfuncionalIndividualRoute(
                            dataRegistro = pensamento.dataHora
                        )
                    )
                },

                onVoltar = {
                    navController.popBackStack()
                }
            )
        }

        // CRIAR RELATO
        composable<CriarRelatoRoute> {

            CriarRelatoScreen(
                dataRegistro = "22/09/2026",
                onVoltar = {
                    navController.popBackStack()
                },
                onSalvar = { titulo, conteudo ->
                    navController.popBackStack()
                }
            )
        }


        // PONTUAÇÃO DIÁRIA
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
fun CriarRelatoScreen(onVoltar: () -> Boolean) {
    TODO("Not yet implemented")
}

@Composable
fun PontuacaoDiariaScreen(onVoltar: () -> Boolean) {
    TODO("Not yet implemented")
}