package domain.use_case

import common.Resource
import data.remote.dto.toBookingDetailList
import domain.model.BookingDetailList
import domain.repository.BookingRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookingUseCase(
    private val repository: BookingRepository,
) {

    fun getBookingList(): Flow<Resource<BookingDetailList>> = flow {
        try {
            emit(Resource.Loading())
            val birdImageList = repository.getBookingDetails().toBookingDetailList()
            emit(Resource.Success(birdImageList))
        } catch (e: IOException) {
            println("IOException called $e")
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))

        }

    }.flowOn(Dispatchers.IO)

}