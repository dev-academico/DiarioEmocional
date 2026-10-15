data class AvaliacaoMock(
    val dataRegistro: String,
    val intensity: Number,
    val descricao: String,
    val gatilhos: List<String>,
)

val avaliacaoMock =
    listOf(
        AvaliacaoMock(
            dataRegistro = "2026-10-05",
            intensity = 3,
            descricao = "Dia tranquilo, consegui manter a rotina sem grandes dificuldades.",
            gatilhos = listOf("Rotina organizada", "Boa noite de sono"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-06",
            intensity = 7,
            descricao = "Senti muita ansiedade antes da reunião de trabalho.",
            gatilhos = listOf("Trabalho", "Prazo apertado", "Excesso de cafeína"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-07",
            intensity = 5,
            descricao = "Dia mediano, com algumas oscilações de humor à tarde.",
            gatilhos = listOf("Cansaço", "Trânsito"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-08",
            intensity = 8,
            descricao = "Discussão com um familiar me deixou muito abalado.",
            gatilhos = listOf("Conflito familiar", "Estresse emocional"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-09",
            intensity = 2,
            descricao = "Me senti leve e disposto, fiz uma caminhada ao ar livre.",
            gatilhos = listOf("Exercício físico", "Contato com a natureza"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-10",
            intensity = 6,
            descricao = "Dificuldade para dormir, acordei várias vezes durante a noite.",
            gatilhos = listOf("Insônia", "Uso excessivo de celular"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-11",
            intensity = 4,
            descricao = "Fim de semana em casa, um pouco de solidão mas nada intenso.",
            gatilhos = listOf("Isolamento", "Falta de atividades"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-12",
            intensity = 9,
            descricao = "Crise intensa após receber uma notícia ruim no trabalho.",
            gatilhos = listOf("Notícia inesperada", "Trabalho", "Pressão"),
        ),
        AvaliacaoMock(
            dataRegistro = "2026-10-13",
            intensity = 1,
            descricao = "Dia muito bom, conversei com amigos e me senti acolhido.",
            gatilhos = listOf("Convívio social", "Apoio de amigos"),
        ),
    )
