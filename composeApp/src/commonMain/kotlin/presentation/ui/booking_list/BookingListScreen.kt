package presentation.ui.booking_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import common.Constant.FILTER_BY_NAME
import di.KoinHelper
import domain.model.Request
import presentation.ui.details.DetailsScreen

class BookingListScreen : Screen {

    var viewModel: BookingListViewModel = KoinHelper().getAppViewModel()

    init {
        viewModel.getBookings()
    }

    @Composable
    override fun Content() {
        Scaffold { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                TopBarSection()
                HotelList(viewModel.uiState.value.bookingList)
            }
        }
    }

    @Composable
    fun HotelList(bookingList: List<Request>) {
        LazyColumn {
            items(bookingList.size) {
                HotelItem(request = bookingList.get(it))
            }
        }
    }

    private @Composable
    fun TopBarSection() {
        var hotelName by remember { mutableStateOf(FILTER_BY_NAME) }
        DynamicSelectTextField(label = FILTER_BY_NAME,
            selectedValue = hotelName,
            options = viewModel.bookingList.map { it.hotel.name }.toSet().toList(),
            modifier = Modifier.padding(16.dp),
            onValueChangedEvent = {
                hotelName = it
                viewModel.selectValue(hotelName)
            })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DynamicSelectTextField(
        selectedValue: String,
        options: List<String>,
        label: String,
        onValueChangedEvent: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedValue,
                onValueChange = {
                },
                label = { Text(text = label) },
                trailingIcon = {
                    if (expanded)
                        Icon(
                            Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Arrow Down"
                        )
                    if (!expanded)
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Arrow Up"
                        )
                },
                colors = OutlinedTextFieldDefaults.colors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option: String ->
                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    })
                }
            }
        }
    }

    @Composable
    fun HotelItem(request: Request) {
        val navigator = LocalNavigator.currentOrThrow
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(
                width = 0.5.dp, color = MaterialTheme.colorScheme.outline
            ), modifier = Modifier.padding(18.dp).clickable {
                navigator.push(DetailsScreen(request))
            }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = request.hotel.name,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = "${request.id}", fontSize = 14.sp, modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = request.created_at,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Text(
                    text = "For ${request.unit}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(4.dp)
                )

            }
        }
    }

}


