package com.suru.routes

import com.suru.Utils
import com.suru.models.BranchRequest
import com.suru.plugins.BadRequestException
import com.suru.services.BranchService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.branchRouting(branchService: BranchService) {
    route("branch") {
        post {
            val request = call.receive<BranchRequest>()
            val response = branchService.create(request)
            call.respond(HttpStatusCode.Created, response)
        }

        get {
            call.respond(branchService.getAll())
        }

        get("/{id}") {
            val id = Utils.toUUID(call.parameters["id"])
            call.respond(branchService.getById(id))
        }

        get("/{shortName}") {
            val shortName = call.parameters["shortName"] ?: throw BadRequestException("invalid short name")
            call.respond(branchService.getByShortName(shortName))
        }

        put("/{id}") {
            val id = Utils.toUUID(call.parameters["id"])
            val request = call.receive<BranchRequest>()
            branchService.update(id, request)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = Utils.toUUID(call.parameters["id"])
            branchService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}