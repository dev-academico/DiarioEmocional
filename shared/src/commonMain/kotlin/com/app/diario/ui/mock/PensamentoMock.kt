data class PensamentoMock(
    val dataHora: String,
    val situacao: String,
    val pensamentosImagens: String,
    val emocoesSentimentos: String,
    val comportamentosReacoes: String
)

val pensamentosDisfuncionaisMock = listOf(
    PensamentoMock(
        dataHora = "09/09/2026 08:30",
        situacao = "Prova da faculdade",
        pensamentosImagens = "Não vou conseguir fazer a prova. Vou esquecer tudo.",
        emocoesSentimentos = "Ansiedade e medo",
        comportamentosReacoes = "Fiquei nervosa e evitei começar a estudar."
    ),

    PensamentoMock(
        dataHora = "08/09/2026 15:00",
        situacao = "Conversa com um amigo",
        pensamentosImagens = "Ele não respondeu porque está chateado comigo.",
        emocoesSentimentos = "Preocupação e insegurança",
        comportamentosReacoes = "Fiquei verificando o celular várias vezes."
    ),

    PensamentoMock(
        dataHora = "07/09/2026 19:30",
        situacao = "Apresentação de um trabalho",
        pensamentosImagens = "Todo mundo vai perceber que estou nervosa.",
        emocoesSentimentos = "Vergonha e ansiedade",
        comportamentosReacoes = "Falei rapidamente e evitei olhar para as pessoas."
    )
)