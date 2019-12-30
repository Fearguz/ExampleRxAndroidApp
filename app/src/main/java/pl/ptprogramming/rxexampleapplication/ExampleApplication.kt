package pl.ptprogramming.rxexampleapplication

import android.app.Application
import org.koin.core.context.startKoin
import pl.ptprogramming.rxexampleapplication.di.exampleModule

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(exampleModule)
        }
    }
}