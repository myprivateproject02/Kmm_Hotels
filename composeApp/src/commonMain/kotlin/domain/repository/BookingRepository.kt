package domain.repository

import data.remote.dto.BookingDetailListDto

interface BookingRepository {
    suspend fun getBookingDetails(): BookingDetailListDto
}