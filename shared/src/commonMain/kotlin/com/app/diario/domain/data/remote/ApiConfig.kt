package com.app.diario.domain.remote


expect object ApiConfig {
    val supabaseUrl: String
    val supabaseAnonKey: String
}
