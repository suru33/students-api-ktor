package com.suru.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable


@Serializable
data class ExceptionMessage(val message: String, val code: Int)

fun Application.configureException() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val logger = call.application.environment.log
            when (cause) {
                is InvalidUserNameException -> {
                    logger.warn("user name not provided")
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = ExceptionMessage(cause.message ?: "", HttpStatusCode.BadRequest.value)
                    )
                }

                else -> {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = ExceptionMessage("something bad happened", HttpStatusCode.InternalServerError.value)
                    )
                }
            }
        }
    }
}

class InvalidUserNameException() : Exception("user is mandatory")
