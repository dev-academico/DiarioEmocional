package com.app.diario.ui.avaliacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.AvaliacaoRepositoryImpl
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.repository.AvaliacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvaliacaoViewModel(
    private val repository: AvaliacaoRepository = AvaliacaoRepositoryImpl(createHttpClient()),
) : ViewModel() {
    private val _estado = MutableStateFlow(AvaliacaoUiState())
    val estado: StateFlow<AvaliacaoUiState> = _estado.asStateFlow()

    init {
        carregar()
    }

    fun carregar() {
        _estado.update { it.copy(tela = EstadoAvaliacaoTela.Carregando) }
        viewModelScope.launch {
            try {
                val lista = repository.buscarAvaliacoes()
                val novaTela =
                    if (lista.isEmpty()) {
                        EstadoAvaliacaoTela.Vazio
                    } else {
                        EstadoAvaliacaoTela.Sucesso(lista)
                    }
                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Falha.SemConexao) {
                _estado.update { it.copy(tela = EstadoAvaliacaoTela.Erro(e.message ?: "")) }
            } catch (e: Falha.RespostaInvalida) {
                _estado.update { it.copy(tela = EstadoAvaliacaoTela.Erro(e.message ?: "")) }
            }
        }
    }

    fun salvar(
        dataRegistro: String,
        intensidade: Int,
        descricao: String,
        gatilhos: List<String>,
        onSucesso: () -> Unit,
        onErro: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                repository.criarAvaliacao(
                    dataRegistro = dataRegistro,
                    intensidade = intensidade,
                    descricao = descricao,
                    gatilhos = gatilhos,
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
