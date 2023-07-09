package com.suru

import DatabaseFactory
import com.suru.plugins.configureException
import com.suru.plugins.configureHTTP
import com.suru.plugins.configureKoin
import com.suru.plugins.configureRouting
import com.suru.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureHTTP()
    configureSerialization()
    configureRouting()
    configureException()
    DatabaseFactory.init(environment.config)
}
