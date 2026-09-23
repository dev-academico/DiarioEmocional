package com.app.diario.ui.mock
import java.time.LocalDate

data class RelatoMock(
    val titulo: String,
    val dataRegistro: String,
    val conteudo: String
)

val relatosMock = listOf(
    RelatoMock(
        titulo = "Um dia tranquilo",
        dataRegistro = "09/09/2026",
        conteudo = "Hoje foi um dia tranquilo. Consegui organizar minhas atividades."
    ),
    RelatoMock(
        titulo = "Dia corrido",
        dataRegistro = "08/09/2026",
        conteudo = "Hoje tive muitas atividades da faculdade."
    ),
    RelatoMock(
        titulo = "Um bom momento",
        dataRegistro =  "07/09/2026",
        conteudo = "Hoje passei um tempo com meus amigos."
    )
)