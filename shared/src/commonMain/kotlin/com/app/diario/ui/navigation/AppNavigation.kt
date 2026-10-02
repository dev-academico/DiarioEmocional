package com.app.diario.ui.navigation

import CriarPensamentoDisfuncionalRoute
import CriarPensamentoDisfuncionalScreen
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
import androidx.annotation.RequiresApi
import androidx.navigation.navDeepLink
import com.app.diario.ui.screens.HomeScreen
import pensamentosDisfuncionaisMock
import kotlin.time.Clock


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


// LISTA DE PENSAMENTOS DISFUNCIONAIS
        composable<PensamentoDisfuncionalRoute> {
            RegistroPensamentoScreen(
                pensamentos = pensamentosDisfuncionaisMock,

                // CELULAR: navega para a página individual.
                // LAYOUT LARGO: a tela intercepta o clique e apenas seleciona.
                onPensamentoIndividual = { pensamento ->
                    navController.navigate(
                        PensamentoDisfuncionalIndividualRoute(
                            dataRegistro = pensamento.dataHora
                        )
                    )
                },

                onNovoPensamentoClick = {
                    navController.navigate(CriarPensamentoDisfuncionalRoute)
                },

                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


// PENSAMENTO DISFUNCIONAL INDIVIDUAL
        composable<PensamentoDisfuncionalIndividualRoute>(
            deepLinks = listOf(
                navDeepLink<PensamentoDisfuncionalIndividualRoute>(
                    basePath = "diario://pensamento"
                )
            )
        ) { backStackEntry ->

            val rota = backStackEntry.toRoute<PensamentoDisfuncionalIndividualRoute>()

            RegistroPensamentoIndividualScreen(
                dataRegistro = rota.dataRegistro,
                onVoltar = {
                    navController.popBackStack()
                }
            )
        }


        composable<CriarPensamentoDisfuncionalRoute> {
            CriarPensamentoDisfuncionalScreen(
                dataHora = Clock.System.now().toString(),
                onVoltar = {
                    navController.popBackStack()
                },
                onSalvar = { situacao, pensamentos, emocoes, comportamentos ->
                    navController.popBackStack()
                }
            )
        }

        // CRIAR RELATO
        composable<CriarRelatoRoute> {

            CriarRelatoScreen(
                dataRegistro = Clock.System.now().toString(),
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
fun PontuacaoDiariaScreen(onVoltar: () -> Boolean) {
    TODO("Not yet implemented")
}