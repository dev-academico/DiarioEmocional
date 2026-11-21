package com.app.diario.ui.pensamentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.PensamentoRepositoryImpl
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.repository.PensamentoRepository
import com.app.diario.ui.mock.PensamentoMock
import com.app.diario.ui.mock.pensamentosDisfuncionaisMock
import io.ktor.client.utils.HttpRequestCreated
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PensamentoViewModel(
    private val repository: PensamentoRepository = PensamentoRepositoryImpl(createHttpClient())
) : ViewModel() {
    private val _estado = MutableStateFlow(PensamentoUiState())
    val estado: StateFlow<PensamentoUiState> = _estado.asStateFlow()


    init {
        carregar()
    }

    fun carregar() {
        _estado.update { it.copy(tela = EstadoTela.Carregando) }
        viewModelScope.launch {
            try {
                val pensamentos = repository.buscarPensamento()
                val tela =
                    if (pensamentos.isEmpty()) {
                        EstadoTela.Vazio
                    } else {
                        EstadoTela.Sucesso(pensamentos)
                    }
                _estado.update { it.copy(tela = tela) }
            } catch (e: Falha.SemConexao) {
                _estado.update { it.copy(tela = EstadoTela.Erro(e.message ?: "")) }
            } catch (e: Falha.RespostaInvalida) {
                _estado.update { it.copy(tela = EstadoTela.Erro(e.message ?: "")) }
            }
        }
    }

    fun salvar(dataRegistro: String,
               situacao: String,
               pensamentosImagens: String,
               emocoesSentimentos: String,
               comportamentosReacoes: String,
               onSucesso: () -> Unit,
               onErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.criarPensamento(
                    dataRegistro,
                    situacao,
                    pensamentosImagens,
                    emocoesSentimentos,
                    comportamentosReacoes
                )
                carregar()
                onSucesso()
            } catch (e: Falha.SemConexao) {
                onErro("${e.message}")
            } catch (e: Falha.RespostaInvalida) {
                onErro("${e.message}")
            }
        }
    }
}
