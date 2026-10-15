package com.app.diario.ui.mock

enum class QualidadeSono(val rotulo: String) {
    PESSIMA("Péssima"),
    RUIM("Ruim"),
    REGULAR("Regular"),
    BOA("Boa"),
    OTIMA("Ótima"),
}

enum class DisposicaoAoAcordar(val rotulo: String) {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
}

data class RegistroSonoMock(
    val dataRegistro: String, // "2026-10-15"
    val horarioDormiu: String, // "HH:mm"
    val horarioAcordou: String, // "HH:mm"
    val qualidade: QualidadeSono,
    val disposicao: DisposicaoAoAcordar,
    val usouCelularAntesDeDormir: Boolean,
    val tevePesadelos: Boolean,
    val comentarioSonhos: String,
)

val registrosSonoMock =
    listOf(
        RegistroSonoMock(
            dataRegistro = "2026-10-15",
            horarioDormiu = "23:30",
            horarioAcordou = "06:45",
            qualidade = QualidadeSono.REGULAR,
            disposicao = DisposicaoAoAcordar.MEDIA,
            usouCelularAntesDeDormir = true,
            tevePesadelos = false,
            comentarioSonhos = "Acordei algumas vezes durante a madrugada, mas voltei a dormir rápido.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-14",
            horarioDormiu = "22:45",
            horarioAcordou = "06:30",
            qualidade = QualidadeSono.BOA,
            disposicao = DisposicaoAoAcordar.ALTA,
            usouCelularAntesDeDormir = false,
            tevePesadelos = false,
            comentarioSonhos = "Sonhei com uma viagem à praia.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-13",
            horarioDormiu = "00:40",
            horarioAcordou = "07:00",
            qualidade = QualidadeSono.RUIM,
            disposicao = DisposicaoAoAcordar.BAIXA,
            usouCelularAntesDeDormir = true,
            tevePesadelos = true,
            comentarioSonhos = "Pesadelo sobre perder uma prova importante. Acordei suando.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-12",
            horarioDormiu = "23:00",
            horarioAcordou = "07:15",
            qualidade = QualidadeSono.OTIMA,
            disposicao = DisposicaoAoAcordar.ALTA,
            usouCelularAntesDeDormir = false,
            tevePesadelos = false,
            comentarioSonhos = "",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-11",
            horarioDormiu = "01:15",
            horarioAcordou = "06:00",
            qualidade = QualidadeSono.PESSIMA,
            disposicao = DisposicaoAoAcordar.BAIXA,
            usouCelularAntesDeDormir = true,
            tevePesadelos = true,
            comentarioSonhos = "Demorei muito para pegar no sono e tive sonhos agitados.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-10",
            horarioDormiu = "23:50",
            horarioAcordou = "07:30",
            qualidade = QualidadeSono.BOA,
            disposicao = DisposicaoAoAcordar.MEDIA,
            usouCelularAntesDeDormir = false,
            tevePesadelos = false,
            comentarioSonhos = "Sonho confuso, não lembro os detalhes.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-09",
            horarioDormiu = "22:30",
            horarioAcordou = "06:15",
            qualidade = QualidadeSono.BOA,
            disposicao = DisposicaoAoAcordar.ALTA,
            usouCelularAntesDeDormir = false,
            tevePesadelos = false,
            comentarioSonhos = "",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-08",
            horarioDormiu = "00:10",
            horarioAcordou = "06:40",
            qualidade = QualidadeSono.REGULAR,
            disposicao = DisposicaoAoAcordar.MEDIA,
            usouCelularAntesDeDormir = true,
            tevePesadelos = false,
            comentarioSonhos = "Usei o celular até tarde e senti a cabeça pesada ao acordar.",
        ),
        RegistroSonoMock(
            dataRegistro = "2026-10-07",
            horarioDormiu = "23:15",
            horarioAcordou = "06:50",
            qualidade = QualidadeSono.REGULAR,
            disposicao = DisposicaoAoAcordar.BAIXA,
            usouCelularAntesDeDormir = true,
            tevePesadelos = false,
            comentarioSonhos = "Sonhei que estava atrasado para o trabalho.",
        ),
    )
