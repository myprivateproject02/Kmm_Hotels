package presentation.ui.booking_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import common.Resource
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.Request
import domain.use_case.BookingUseCase
import kotlinx.coroutines.launch

class BookingListViewModel(
    private var getBookingUseCase: BookingUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(BookingUiState())
    val uiState: State<BookingUiState> = _uiState

    var bookingList: MutableList<Request> = mutableListOf()

    fun getBookings() {
        viewModelScope.launch {
            getBookingUseCase.getBookingList().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value = BookingUiState(
                            bookingList = emptyList(),
                            isLoading = false,
                            errorMessage = "Something Went Wrong!"
                        )
                    }

                    is Resource.Loading -> {
                        _uiState.value = BookingUiState(
                            bookingList = emptyList(),
                            isLoading = true,
                            errorMessage = ""
                        )
                    }

                    is Resource.Success -> {
                        println("Resource.Success called ${result.data}")
                        bookingList.clear()
                        bookingList.addAll(result.data?.requests ?: emptyList())
                        _uiState.value = BookingUiState(
                            bookingList = bookingList,
                            isLoading = false,
                            errorMessage = ""
                        )
                    }
                }
            }
        }
    }

    fun selectValue(value: String) {
        val filteredList = bookingList.filter { it.hotel.name == value }
        if (_uiState.value.selectedFilter == value) {
            _uiState.value.copy(selectedFilter = null)
        } else {
            _uiState.value = BookingUiState(
                bookingList = filteredList,
                isLoading = false,
                errorMessage = "",
                selectedFilter = value
            )
        }
    }

}