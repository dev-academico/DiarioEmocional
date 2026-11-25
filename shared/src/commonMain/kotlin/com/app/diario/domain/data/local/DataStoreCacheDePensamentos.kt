package com.app.diario.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.diario.domain.data.remote.dto.PensamentoDto
import com.app.diario.domain.data.remote.dto.paraDto
import com.app.diario.domain.data.remote.dto.toDomain
import com.app.diario.domain.model.Pensamento
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreCacheDePensamentos(
    private val dataStore: DataStore<Preferences>,
) : CacheDePensamentos {
    private val chavePensamentos = stringPreferencesKey("pensamentos_json")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun salvar(pensamentos: List<Pensamento>) {
        val dtos = pensamentos.map { it.paraDto() }
        dataStore.edit { preferencias ->
            preferencias[chavePensamentos] = json.encodeToString(dtos)
        }
    }

    override suspend fun ler(): List<Pensamento>? {
        val texto = dataStore.data.first()[chavePensamentos] ?: return null
        return json.decodeFromString<List<PensamentoDto>>(texto).map { it.toDomain() }
    }
}
