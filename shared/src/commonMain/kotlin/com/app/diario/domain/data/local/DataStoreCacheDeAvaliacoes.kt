package com.app.diario.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.diario.domain.data.remote.dto.AvaliacaoDto
import com.app.diario.domain.data.remote.dto.paraDominio
import com.app.diario.domain.data.remote.dto.paraDto
import com.app.diario.domain.model.Avaliacao
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreCacheDeAvaliacoes(
    private val dataStore: DataStore<Preferences>,
) : CacheDeAvaliacoes {
    private val chaveAvaliacoes = stringPreferencesKey("avaliacoes_json")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun salvar(avaliacoes: List<Avaliacao>) {
        val dtos = avaliacoes.map { it.paraDto() }
        dataStore.edit { preferencias ->
            preferencias[chaveAvaliacoes] = json.encodeToString(dtos)
        }
    }

    override suspend fun ler(): List<Avaliacao>? {
        val texto = dataStore.data.first()[chaveAvaliacoes] ?: return null
        return json.decodeFromString<List<AvaliacaoDto>>(texto).map { it.paraDominio() }
    }
}
