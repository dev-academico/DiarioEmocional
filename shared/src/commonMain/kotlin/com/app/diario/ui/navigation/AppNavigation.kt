package com.app.diario.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.app.diario.ui.HomeScreen
import com.app.diario.ui.avaliacao.AvaliacaoIndividualScreen
import com.app.diario.ui.avaliacao.AvaliacaoSentimentoScreen
import com.app.diario.ui.avaliacao.CriarAvaliacaoScreen
import com.app.diario.ui.higiene.CriarSonoScreen
import com.app.diario.ui.higiene.HigieneSonoIndividualScreen
import com.app.diario.ui.higiene.HigieneSonoScreen
import com.app.diario.ui.mock.*
import com.app.diario.ui.pensamentos.CriarPensamentoDisfuncionalScreen
import com.app.diario.ui.pensamentos.RegistroPensamentoIndividualScreen
import com.app.diario.ui.pensamentos.RegistroPensamentoScreen
import com.app.diario.ui.relatos.CriarRelatoScreen
import com.app.diario.ui.relatos.RelatoIndividualScreen
import com.app.diario.ui.relatos.RelatosScreen
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
                    dataRegistro = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
                        .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                        .format(java.util.Date()),
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
                    onAvaliacaoIndividual = {
                        navController.navigate(AvaliacaoIndividualRoute(it.dataRegistro))
                    },
                    onNovaAvaliacaoClick = { navController.navigate(CriarAvaliacaoSentimento) },
                    onVoltar = { navController.popBackStack() },
                )
            }

            composable<CriarAvaliacaoSentimento> {
                CriarAvaliacaoScreen(
                    dataRegistro = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
                        .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                        .format(java.util.Date()),
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
                    dataRegistro = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
                        .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                        .format(java.util.Date()),
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
                    dataRegistro = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US)
                        .apply { timeZone = java.util.TimeZone.getTimeZone("UTC") }
                        .format(java.util.Date()),
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
