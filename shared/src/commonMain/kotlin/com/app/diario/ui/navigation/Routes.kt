import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object RelatosRoute

@Serializable
object PensamentoDisfuncionalRoute

@Serializable
object PontuacaoDiariaRoute

@kotlinx.serialization.Serializable
data class RelatoIndividualRoute(
    val dataRegistro: String
)