package com.app.diario.domain.data.remote

actual object ApiConfig {
    actual val supabaseUrl: String = when (val valor = System.getenv("SUPABASE_URL")) {
        null, "" -> "https://bboiigngnkifqnpvaswm.supabase.co"
        else -> valor
    }

    actual val supabaseAnonKey: String = when (val valor = System.getenv("SUPABASE_ANON_KEY")) {
        null, "" -> "sb_publishable_o8nkXMjnPmcPn_WGbPgpkg_qwjz6cpb"
        else -> valor
    }
}