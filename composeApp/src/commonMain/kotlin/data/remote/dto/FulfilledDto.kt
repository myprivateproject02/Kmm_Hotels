package data.remote.dto


import domain.model.Fulfilled
import domain.model.Hotel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FulfilledDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("username")
    val username: String?
)

fun FulfilledDto.toFulfilled(): Fulfilled {
    return Fulfilled(
        id = id ?: 0,
        username = username ?: ""
    )
}