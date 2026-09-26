data class PontuacaoMock(
    val realizado: Boolean,
    val dataRegistro: String,
    val diaSemana: String
)

val pontuacoesMock = listOf(
    PontuacaoMock(
        realizado = true,
        dataRegistro = "09/09/2026",
        diaSemana = "quarta-feira"
    ),
    PontuacaoMock(
        realizado = true,
        dataRegistro = "08/09/2026",
        diaSemana = "terça-feira"
    ),
    PontuacaoMock(
        realizado = true,
        dataRegistro = "07/09/2026",
        diaSemana = "segunda-feira"
    ),
    PontuacaoMock(
        realizado = false,
        dataRegistro = "06/09/2026",
        diaSemana = "domingo"
    )
)