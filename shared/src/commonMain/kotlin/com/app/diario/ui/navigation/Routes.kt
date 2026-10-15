import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object RelatosRoute

@Serializable
object PensamentoDisfuncionalRoute

@Serializable
data object CriarRelatoRoute

@Serializable
data object CriarPensamentoDisfuncionalRoute

@Serializable
object PontuacaoDiariaRoute

@Serializable
data class RelatoIndividualRoute(
    val dataRegistro: String,
)

@Serializable
data class PensamentoDisfuncionalIndividualRoute(
    val dataRegistro: String,
)

@Serializable
data object AvaliacaoRoute

@Serializable
data class AvaliacaoIndividualRoute(
    val dataRegistro: String,
)

@Serializable
data object CriarAvaliacaoSentimento