import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.ui.booking_list.BookingListScreen

@Composable
@Preview
fun App() {
    initKoin()
    AppTheme {
        val scope = rememberCoroutineScope()
        Navigator(BookingListScreen())
    }

}