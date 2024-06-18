package data.remote.dto


import domain.model.Desk
import domain.model.Hotel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeskDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?
)


fun DeskDto.toDesk(): Desk {
    return Desk(
        id = id ?: 0,
        name = name ?: "",
    )
}