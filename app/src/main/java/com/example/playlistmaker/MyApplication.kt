package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.di.repositoryModule
import com.example.playlistmaker.ui.view_model.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Запускаем Koin
        startKoin {
            val viewModelModule = module {
                viewModel {
                    SearchViewModel(get()) // Koin сам подставит репозиторий
                }
            }
            // Подключаем модули
            modules(repositoryModule, viewModelModule)
        }
    }
}