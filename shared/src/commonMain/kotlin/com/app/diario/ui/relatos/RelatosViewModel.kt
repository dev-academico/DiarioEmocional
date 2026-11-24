package com.app.diario.ui.relatos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.RelatoRepositoryImpl
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.repository.RelatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RelatosViewModel(
    private val repository: RelatoRepository = RelatoRepositoryImpl(createHttpClient()),
) : ViewModel() {
    private val _estado = MutableStateFlow(RelatosUiState())
    val estado: StateFlow<RelatosUiState> = _estado.asStateFlow()

    init {
        carregar()
    }

    fun carregar() {
        viewModelScope.launch {
            _estado.update { it.copy(tela = EstadoTela.Carregando) }
            try {
                val relatos = repository.buscarRelatos()
                val tela =
                    if (relatos.isEmpty()) {
                        EstadoTela.Vazio
                    } else {
                        EstadoTela.Sucesso(relatos)
                    }
                _estado.update { it.copy(tela = tela) }
            } catch (e: Falha.SemConexao) {
                _estado.update { it.copy(tela = EstadoTela.Erro(e.message ?: "")) }
            } catch (e: Falha.RespostaInvalida) {
                _estado.update { it.copy(tela = EstadoTela.Erro(e.message ?: "")) }
            }
        }
    }

    fun salvar(
        titulo: String,
        conteudo: String,
        dataRegistro: String,
        onSucesso: () -> Unit,
        onErro: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                repository.criarRelato(titulo = titulo, dataRegistro = dataRegistro, conteudo = conteudo)
                carregar()
                onSucesso()
            } catch (e: Falha.RespostaInvalida) {
                onErro("${e.message}")
            } catch (e: Falha.SemConexao) {
                onErro("${e.message}")
            }
        }
    }
}
