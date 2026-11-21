package com.app.diario.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.app.diario.ui.pensamentos.PensamentoViewModel
import com.app.diario.ui.pensamentos.RegistroPensamentoIndividualScreen
import com.app.diario.ui.pensamentos.RegistroPensamentoScreen
import com.app.diario.ui.relatos.CriarRelatoScreen
import com.app.diario.ui.relatos.RelatoIndividualScreen
import com.app.diario.ui.relatos.RelatosScreen
import com.app.diario.ui.relatos.RelatosViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route?.contains(item.route::class.simpleName ?: "") == true
                    } == true

                    NavigationBarItem(
                        selected = isSelected,
                        label = { Text(item.title) },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = RelatosGraphRoute,
            modifier = Modifier.padding(paddingValues)
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
                composable<RelatosRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<RelatosGraphRoute>()
                    }
                    val viewModel: RelatosViewModel = viewModel(parentEntry) { RelatosViewModel() }

                    RelatosScreen(
                        viewModel = viewModel,
                        onRelatoIndividual = { relato ->
                            navController.navigate(
                                RelatoIndividualRoute(dataRegistro = relato.dataRegistro)
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

                composable<CriarRelatoRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<RelatosGraphRoute>()
                    }
                    val viewModel: RelatosViewModel = viewModel(parentEntry) { RelatosViewModel() }
                    val hoje = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
                    var mensagemErroSalvar by remember { mutableStateOf<String?>(null) }

                    CriarRelatoScreen(
                        dataRegistro = hoje,
                        mensagemErroSalvar = mensagemErroSalvar,
                        onVoltar = {
                            navController.popBackStack()
                        },
                        onSalvar = { titulo, conteudo ->
                            mensagemErroSalvar = null
                            viewModel.salvar(
                                titulo = titulo,
                                conteudo = conteudo,
                                dataRegistro = hoje,
                                onSucesso = {
                                    navController.popBackStack()
                                },
                                onErro = { mensagemErro ->
                                    mensagemErroSalvar = mensagemErro
                                }
                            )
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
                        dataRegistro = java.text.SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                            java.util.Locale.US
                        )
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
                composable<PensamentoDisfuncionalRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<PensamentoGraphRoute>()
                    }

                    val viewModel: PensamentoViewModel =
                        viewModel(parentEntry) { PensamentoViewModel() }

                    RegistroPensamentoScreen(
                        viewModel = viewModel,
                        onPensamentoIndividual = { pensamento ->
                            navController.navigate(
                                PensamentoDisfuncionalIndividualRoute(
                                    id = pensamento.id,
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
                        id = rota.id,
                        onVoltar = {
                            navController.popBackStack()
                        },
                    )
                }

                composable<CriarPensamentoDisfuncionalRoute> { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry<PensamentoGraphRoute>()
                    }

                    val viewModel: PensamentoViewModel =
                        viewModel(parentEntry) { PensamentoViewModel() }
                    val hoje = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
                    var mensagemErroSalvar by remember { mutableStateOf<String?>(null) }

                    CriarPensamentoDisfuncionalScreen(
                        mensagemErroSalvar = mensagemErroSalvar,
                        dataRegistro = hoje,
                        onVoltar = {
                            navController.popBackStack()
                        },
                        onSalvar = { situacao: String,
                                     pensamentosImagens: String,
                                     emocoesSentimentos: String,
                                     comportamentosReacoes: String ->
                            viewModel.salvar(
                                hoje,
                                situacao,
                                pensamentosImagens,
                                emocoesSentimentos,
                                comportamentosReacoes,
                                onSucesso = {
                                    navController.popBackStack()
                                },
                                onErro = { mensagemErro ->
                                    mensagemErroSalvar = mensagemErro
                                }
                            )
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
                        dataRegistro = java.text.SimpleDateFormat(
                            "yyyy-MM-dd'T'HH:mm:ss'Z'",
                            java.util.Locale.US
                        )
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
}