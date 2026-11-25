package com.app.diario.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.diario.domain.data.remote.dto.RelatoDto
import com.app.diario.domain.data.remote.dto.paraDominio
import com.app.diario.domain.data.remote.dto.paraDto
import com.app.diario.domain.model.Relato
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreCacheDeRelatos(
    private val dataStore: DataStore<Preferences>,
) : CacheDeRelatos {
    private val chaveRelatos = stringPreferencesKey("relatos_json")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun salvar(relatos: List<Relato>) {
        val dtos = relatos.map { it.paraDto() }
        dataStore.edit { preferencias ->
            preferencias[chaveRelatos] = json.encodeToString(dtos)
        }
    }

    override suspend fun ler(): List<Relato>? {
        val texto = dataStore.data.first()[chaveRelatos] ?: return null
        return json.decodeFromString<List<RelatoDto>>(texto).map { it.paraDominio() }
    }
}
