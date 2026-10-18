package com.app.diario.ui.navigation

import AvaliacaoGraphRoute
import AvaliacaoIndividualRoute
import AvaliacaoRoute
import CriarAvaliacaoSentimento
import CriarHigieneSonoRoute
import CriarPensamentoDisfuncionalRoute
import CriarPensamentoDisfuncionalScreen
import CriarRelatoRoute
import CriarRelatoScreen
import HigieneSonoGraphRoute
import HigieneSonoIndividualRoute
import HigieneSonoRoute
import HomeRoute
import PensamentoDisfuncionalIndividualRoute
import PensamentoDisfuncionalRoute
import PensamentoGraphRoute
import RegistroPensamentoIndividualScreen
import RegistroPensamentoScreen
import RelatoIndividualRoute
import RelatoIndividualScreen
import RelatosGraphRoute
import RelatosRoute
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import avaliacaoMock
import com.app.diario.ui.mock.registrosSonoMock
import com.app.diario.ui.mock.relatosMock
import com.app.diario.ui.screen.AvaliacaoIndividualScreen
import com.app.diario.ui.screen.AvaliacaoSentimentoScreen
import com.app.diario.ui.screen.CriarAvaliacaoScreen
import com.app.diario.ui.screen.CriarSonoScreen
import com.app.diario.ui.screen.HigieneSonoIndividualScreen
import com.app.diario.ui.screen.HigieneSonoScreen
import com.app.diario.ui.screen.RelatosScreen
import com.app.diario.ui.screens.HomeScreen
import pensamentosDisfuncionaisMock
import kotlin.time.Clock

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        // HOME
        composable<HomeRoute> {
            HomeScreen(
                onRelatosClick = {
                    navController.navigate(RelatosGraphRoute)
                },
                onPensamentosClick = {
                    navController.navigate(PensamentoGraphRoute)
                },
                onAvaliacaoClick = {
                    navController.navigate(AvaliacaoGraphRoute)
                },
                onHigieneClick = {
                    navController.navigate(HigieneSonoGraphRoute)
                },
            )
        }

        // grafo dos relatos
        navigation<RelatosGraphRoute>(startDestination = RelatosRoute) {
            composable<RelatosRoute> {
                RelatosScreen(
                    onRelatoIndividual = { relato ->
                        navController.navigate(
                            RelatoIndividualRoute(
                                dataRegistro = relato.dataRegistro,
                            ),
                        )
                    },
                    onNovoRelatoClick = {
                        navController.navigate(CriarRelatoRoute)
                    },
                    onVoltar = {
                        navController.popBackStack()
                    },
                )
            }

            composable<RelatoIndividualRoute>(
                deepLinks =
                    listOf(
                        navDeepLink<RelatoIndividualRoute>(
                            basePath = "diario://relato",
                        ),
                    ),
            ) { backStackEntry ->
                val rota = backStackEntry.toRoute<RelatoIndividualRoute>()
                RelatoIndividualScreen(
                    dataRegistro = rota.dataRegistro,
                    onVoltar = {
                        navController.popBackStack()
                    },
                )
            }

            composable<CriarRelatoRoute> {
                CriarRelatoScreen(
                    dataRegistro = Clock.System.now().toString(),
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onSalvar = { _, _ ->
                        navController.popBackStack()
                    },
                )
            }
        }

        // grafo das avaliações
        navigation<AvaliacaoGraphRoute>(startDestination = AvaliacaoRoute) {
            composable<AvaliacaoRoute> {
                AvaliacaoSentimentoScreen(
                    avaliacoes = avaliacaoMock,
                    onAvaliacaoIndividual = {
                        navController.navigate(AvaliacaoIndividualRoute(it.dataRegistro))
                    },
                    onNovaAvaliacaoClick = { navController.navigate(CriarAvaliacaoSentimento) },
                    onVoltar = { navController.popBackStack() },
                )
            }

            composable<CriarAvaliacaoSentimento> {
                CriarAvaliacaoScreen(
                    dataRegistro = Clock.System.now().toString().substringBefore("T"),
                    onVoltar = { navController.popBackStack() },
                    onSalvar = { _, _, _ -> navController.popBackStack() },
                )
            }

            composable<AvaliacaoIndividualRoute>(
                deepLinks =
                    listOf(
                        navDeepLink<AvaliacaoIndividualRoute>(
                            basePath = "diario://avaliacao",
                        ),
                    ),
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<AvaliacaoIndividualRoute>()
                val avaliacao = avaliacaoMock.find { it.dataRegistro == route.dataRegistro }

                AvaliacaoIndividualScreen(
                    avaliacao = avaliacao,
                    onVoltar = { navController.popBackStack() },
                )
            }
        }

        // grafo dos pensamentos
        navigation<PensamentoGraphRoute>(startDestination = PensamentoDisfuncionalRoute) {
            composable<PensamentoDisfuncionalRoute> {
                RegistroPensamentoScreen(
                    pensamentos = pensamentosDisfuncionaisMock,
                    onPensamentoIndividual = { pensamento ->
                        navController.navigate(
                            PensamentoDisfuncionalIndividualRoute(
                                dataRegistro = pensamento.dataRegistro,
                            ),
                        )
                    },
                    onNovoPensamentoClick = {
                        navController.navigate(CriarPensamentoDisfuncionalRoute)
                    },
                    onVoltar = {
                        navController.popBackStack()
                    },
                )
            }

            composable<PensamentoDisfuncionalIndividualRoute>(
                deepLinks =
                    listOf(
                        navDeepLink<PensamentoDisfuncionalIndividualRoute>(
                            basePath = "diario://pensamento",
                        ),
                    ),
            ) { backStackEntry ->
                val rota = backStackEntry.toRoute<PensamentoDisfuncionalIndividualRoute>()

                RegistroPensamentoIndividualScreen(
                    dataRegistro = rota.dataRegistro,
                    onVoltar = {
                        navController.popBackStack()
                    },
                )
            }

            composable<CriarPensamentoDisfuncionalRoute> {
                CriarPensamentoDisfuncionalScreen(
                    dataRegistro = Clock.System.now().toString().substringBefore("T"),
                    onVoltar = {
                        navController.popBackStack()
                    },
                    onSalvar = { _, _, _, _ ->
                        navController.popBackStack()
                    },
                )
            }
        }

        // grafo da higiene do sono
        navigation<HigieneSonoGraphRoute>(startDestination = HigieneSonoRoute) {
            composable<HigieneSonoRoute> {
                HigieneSonoScreen(
                    registros = registrosSonoMock,
                    onRegistroIndividual = {
                        navController.navigate(
                            HigieneSonoIndividualRoute(dataRegistro = it.dataRegistro),
                        )
                    },
                    onNovoRegistroClick = { navController.navigate(CriarHigieneSonoRoute) },
                    onVoltar = { navController.popBackStack() },
                )
            }

            composable<CriarHigieneSonoRoute> {
                CriarSonoScreen(
                    dataRegistro = Clock.System.now().toString().substringBefore("T"),
                    onVoltar = { navController.popBackStack() },
                    onSalvar = { _, _, _, _, _, _, _ -> navController.popBackStack() },
                )
            }

            composable<HigieneSonoIndividualRoute>(
                deepLinks =
                    listOf(
                        navDeepLink<HigieneSonoIndividualRoute>(
                            basePath = "diario://higiene-sono",
                        ),
                    ),
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<HigieneSonoIndividualRoute>()
                val registro = registrosSonoMock.find { it.dataRegistro == route.dataRegistro }

                HigieneSonoIndividualScreen(
                    registroSonoMock = registro,
                    onVoltar = { navController.popBackStack() },
                )
            }
        }
    }
}
