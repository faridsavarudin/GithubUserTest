package com.test.tix.core.app.di

import com.test.tix.detail.DetailViewModel
import com.test.tix.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel { parameter ->
        DetailViewModel(get(), parameter.get())
    }
}