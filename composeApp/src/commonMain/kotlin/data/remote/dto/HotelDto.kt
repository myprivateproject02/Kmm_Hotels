package data.remote.dto


import domain.model.Hotel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("shortname")
    val shortname: String?
)

fun HotelDto.toHotel(): Hotel {
    return Hotel(
        id = id ?: 0,
        name = name ?: "",
        shortname = shortname ?: ""
    )
}