package com.app.diario.domain.model

data class Listagem<T>(
    val itens: List<T>,
    val doCache: Boolean = false,
)
