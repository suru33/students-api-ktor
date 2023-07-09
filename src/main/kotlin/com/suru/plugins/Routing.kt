package com.suru.plugins

import com.suru.models.BranchRequest
import com.suru.services.BranchService
import com.suru.services.TestService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.request.receive
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val testService by inject<TestService>()
    val branchService by inject<BranchService>()
    install(Resources)

    routing {
        get("/hello") {
            val user = call.request.queryParameters["user"] ?: throw InvalidUserNameException()
            call.respondText(testService.greetUser(user))
        }
        post("/branch") {
            val request = call.receive<BranchRequest>()
            val response = branchService.create(request)
            call.respond(HttpStatusCode.Created, response)
        }
    }
}
