package com.app.diario.domain.data.remote


expect object ApiConfig {
    val supabaseUrl: String
    val supabaseAnonKey: String
}
