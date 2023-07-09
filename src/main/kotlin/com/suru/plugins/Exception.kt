package com.suru.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import java.util.UUID
import kotlinx.serialization.Serializable


@Serializable
data class ExceptionMessage(val message: String, val code: Int)

fun Application.configureException() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            val logger = call.application.environment.log
            when (cause) {
                is InvalidUserNameException, is BadRequestException -> {
                    logger.warn(cause.message)
                    respondBadRequest(call, cause)
                }

                is io.ktor.server.plugins.BadRequestException -> {
                    respondBadRequest(call, java.lang.Exception("invalid request body"))
                }

                is BranchNotFoundException -> {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ExceptionMessage("branch not found", HttpStatusCode.NotFound.value)
                    )
                }

                is DatabaseException -> {
                    logger.error("db exception has occurred", cause)
                    respondInternalServerError(call)
                }

                else -> {
                    respondInternalServerError(call)
                }
            }
        }
    }
}

private suspend fun respondInternalServerError(call: ApplicationCall) {
    call.respond(
        status = HttpStatusCode.InternalServerError,
        message = ExceptionMessage("something bad happened", HttpStatusCode.InternalServerError.value)
    )
}

private suspend fun respondBadRequest(call: ApplicationCall, throwable: Throwable) {
    call.respond(
        status = HttpStatusCode.BadRequest,
        message = ExceptionMessage(throwable.message ?: "", HttpStatusCode.BadRequest.value)
    )
}

class InvalidUserNameException : Exception("user is mandatory")

class DatabaseException(message: String) : Exception(message)

class BadRequestException(message: String) : Exception(message)
class BranchNotFoundException : Exception {
    constructor(branchId: UUID) : super("branch not found id=$branchId")
    constructor(shortName: String) : super("branch not found shortName=$shortName")
}