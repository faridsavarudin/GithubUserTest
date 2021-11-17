package com.test.tix.core.app.di

import com.test.tix.core.app.repository.TixRepository
import com.test.tix.core.app.source.RemoteDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource() }
    single {
        TixRepository(get())
    }
}