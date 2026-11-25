package com.app.diario.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.diario.domain.data.remote.dto.HigieneSonoDto
import com.app.diario.domain.data.remote.dto.paraDominio
import com.app.diario.domain.data.remote.dto.paraDto
import com.app.diario.domain.model.HigieneSono
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataStoreCacheDeHigieneSono(
    private val dataStore: DataStore<Preferences>,
) : CacheDeHigieneSono {
    private val chaveHigieneSono = stringPreferencesKey("higiene_sono_json")
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun salvar(registros: List<HigieneSono>) {
        val dtos = registros.map { it.paraDto() }
        dataStore.edit { preferencias ->
            preferencias[chaveHigieneSono] = json.encodeToString(dtos)
        }
    }

    override suspend fun ler(): List<HigieneSono>? {
        val texto = dataStore.data.first()[chaveHigieneSono] ?: return null
        return json.decodeFromString<List<HigieneSonoDto>>(texto).map { it.paraDominio() }
    }
}
