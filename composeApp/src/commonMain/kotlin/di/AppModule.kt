package di

import common.Constant.BASE_URL
import data.repository.BookingRepositoryImpl
import domain.repository.BookingRepository
import domain.use_case.BookingUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.ui.booking_list.BookingListViewModel

fun initKoin() = run {
    startKoin {
        modules(
            ktorModule,
            repositoryModule,
            useCasesModule,
            viewmodelModule,
        )
    }
}


val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }
}

val repositoryModule = module {
    single<BookingRepository> { BookingRepositoryImpl(get()) }
}

val viewmodelModule = module {
    factory { BookingListViewModel(get()) }
}

val useCasesModule: Module = module {
    factory { BookingUseCase(get()) }
}

class KoinHelper : KoinComponent {
    fun getAppViewModel() = get<BookingListViewModel>()
}