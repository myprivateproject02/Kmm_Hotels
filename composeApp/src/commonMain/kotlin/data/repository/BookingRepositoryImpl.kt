package data.repository

import common.Constant.BASE_URL
import common.Constant.END_POINT
import data.remote.dto.BookingDetailListDto
import domain.repository.BookingRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BookingRepositoryImpl(
    private val httpClient: HttpClient,
) : BookingRepository {
    override suspend fun getBookingDetails(): BookingDetailListDto {
        val result = httpClient.get("$BASE_URL$END_POINT").body<BookingDetailListDto>()
        return result
    }
}