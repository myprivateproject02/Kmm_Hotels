package data.remote.dto


import domain.model.BookingDetailList
import domain.model.Hotel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookingDetailListDto(
    @SerialName("info")
    val info: String?,
    @SerialName("requests")
    val requests: List<RequestDto>
)


fun BookingDetailListDto.toBookingDetailList(): BookingDetailList {
    return BookingDetailList(
        info = info ?: "",
        requests = requests.map { it.toRequest() }
    )
}
