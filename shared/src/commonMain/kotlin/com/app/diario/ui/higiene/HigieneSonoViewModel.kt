package com.app.diario.ui.higiene

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diario.domain.data.remote.createHttpClient
import com.app.diario.domain.data.repository.HigieneSonoRepositoryImpl
import com.app.diario.domain.model.DisposicaoAoAcordar
import com.app.diario.domain.model.Falha
import com.app.diario.domain.model.QualidadeSono
import com.app.diario.domain.model.repository.HigieneSonoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HigieneSonoViewModel(
    private val repository: HigieneSonoRepository = HigieneSonoRepositoryImpl(createHttpClient()),
) : ViewModel() {
    private val _estado = MutableStateFlow(HigieneSonoUiState())
    val estado: StateFlow<HigieneSonoUiState> = _estado.asStateFlow()

    init {
        carregar()
    }

    fun carregar() {
        _estado.update { it.copy(tela = EstadoSonoTela.Carregando) }
        viewModelScope.launch {
            try {
                val listagem = repository.buscarHigieneSono()
                val novaTela =
                    if (listagem.itens.isEmpty()) {
                        EstadoSonoTela.Vazio
                    } else {
                        EstadoSonoTela.Sucesso(
                            registros = listagem.itens,
                            doCache = listagem.doCache,
                        )
                    }
                _estado.update { it.copy(tela = novaTela) }
            } catch (e: Falha.SemConexao) {
                _estado.update { it.copy(tela = EstadoSonoTela.Erro(e.message ?: "")) }
            } catch (e: Falha.RespostaInvalida) {
                _estado.update { it.copy(tela = EstadoSonoTela.Erro(e.message ?: "")) }
            }
        }
    }

    fun salvar(
        dataRegistro: String,
        horarioDormiu: String,
        horarioAcordou: String,
        qualidade: QualidadeSono,
        disposicao: DisposicaoAoAcordar,
        usouCelularAntesDeDormir: Boolean,
        tevePesadelos: Boolean,
        comentarioSonhos: String,
        onSucesso: () -> Unit,
        onErro: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                repository.criarHigieneSono(
                    dataRegistro = dataRegistro,
                    horarioDormiu = horarioDormiu,
                    horarioAcordou = horarioAcordou,
                    qualidade = qualidade,
                    disposicao = disposicao,
                    usouCelularAntesDeDormir = usouCelularAntesDeDormir,
                    tevePesadelos = tevePesadelos,
                    comentarioSonhos = comentarioSonhos,
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
