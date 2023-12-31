package com.suru.plugins

import com.suru.repositories.BranchRepository
import com.suru.repositories.BranchRepositoryImpl
import com.suru.repositories.TestRepository
import com.suru.repositories.TestRepositoryImpl
import com.suru.services.BranchService
import com.suru.services.TestService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val koinModule = module {
    single<TestRepository> { TestRepositoryImpl() }
    single<BranchRepository> { BranchRepositoryImpl() }
    single { TestService(get()) }
    single { BranchService(get()) }
}

fun Application.configureKoin() {
    install(Koin) {
        modules(koinModule)
    }
}