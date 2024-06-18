package data.remote.dto


import domain.model.Hotel
import domain.model.Request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDto(
    @SerialName("comments")
    val comments: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("desk")
    val desk: DeskDto,
    @SerialName("fulfilled_by")
    val fulfilledBy: FulfilledDto,
    @SerialName("hotel")
    val hotel: HotelDto,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("unit")
    val unit: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)

fun RequestDto.toRequest(): Request {
    return Request(
        id = id ?: 0,
        name = name ?: "",
        comments = comments ?: "",
        unit = unit ?: "",
        desk = desk.toDesk(),
        hotel = hotel.toHotel(),
        fulfilled_by = fulfilledBy.toFulfilled(),
        created_at = createdAt ?: "",
        updated_at = updatedAt ?: ""
    )
}