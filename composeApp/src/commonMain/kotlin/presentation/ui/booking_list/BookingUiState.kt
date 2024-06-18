package presentation.ui.booking_list

import domain.model.Request

data class BookingUiState(
    var bookingList: List<Request> = emptyList(),
    var errorMessage: String = "",
    var isLoading: Boolean = false,
    var selectedFilter: String? = null,
) {
    val hotels = bookingList.map { it.hotel.name }.toSet()
    val selectedHotel = bookingList.filter { it.hotel.name == selectedFilter }
}