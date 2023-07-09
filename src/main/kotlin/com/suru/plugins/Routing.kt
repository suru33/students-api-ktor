package com.suru.plugins

import com.suru.services.TestService
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val testService by inject<TestService>()
    install(Resources)

    routing {
        get("/hello") {
            val user = call.request.queryParameters["user"] ?: throw InvalidUserNameException()
            call.respondText(testService.greetUser(user))
        }
    }
}

@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new")
