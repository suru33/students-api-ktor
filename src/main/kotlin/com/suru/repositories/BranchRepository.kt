package com.suru.repositories

import DatabaseFactory.dbQuery
import com.suru.models.BranchRequest
import com.suru.models.BranchResponse
import com.suru.models.Branches
import java.time.LocalDateTime
import java.util.UUID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

interface BranchRepository {
    suspend fun create(request: BranchRequest): BranchResponse
    suspend fun getById(id: UUID): BranchResponse
    suspend fun getByShortName(id: UUID): BranchResponse
    suspend fun update(request: BranchRequest): BranchResponse
}

class BranchRepositoryImpl : BranchRepository {
    private fun toBranchResponse(row: ResultRow) = BranchResponse(
        id = row[Branches.id],
        shortName = row[Branches.shortName],
        name = row[Branches.name],
        createdAt = row[Branches.createdAt],
        updatedAt = row[Branches.updatedAt]
    )


    override suspend fun create(request: BranchRequest): BranchResponse = dbQuery {
        val result = Branches.insert {
            it[id] = UUID.randomUUID()
            it[shortName] = request.shortName
            it[name] = request.name
            it[createdAt] = LocalDateTime.now()
            it[updatedAt] = LocalDateTime.now()
        }
        result.resultedValues?.singleOrNull()?.let(::toBranchResponse)!!
    }

    override suspend fun getById(id: UUID): BranchResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getByShortName(id: UUID): BranchResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(request: BranchRequest): BranchResponse {
        TODO("Not yet implemented")
    }
}