package com.app.diario.domain.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

object CacheDataStoreFactory {
    private val instancias = mutableMapOf<String, DataStore<Preferences>>()

    fun obter(pastaDados: String): DataStore<Preferences> {
        val caminho = "$pastaDados/diario_cache.preferences_pb"
        return instancias.getOrPut(caminho) {
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { caminho.toPath() },
            )
        }
    }
}
