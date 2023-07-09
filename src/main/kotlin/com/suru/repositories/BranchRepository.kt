package com.suru.repositories

import DatabaseFactory.dbQuery
import com.suru.models.BranchRequest
import com.suru.models.BranchResponse
import com.suru.models.Branches
import com.suru.plugins.BranchNotFoundException
import com.suru.plugins.DatabaseException
import io.ktor.util.logging.KtorSimpleLogger
import java.time.LocalDateTime
import java.util.UUID
import kotlin.reflect.jvm.jvmName
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

interface BranchRepository {
    suspend fun create(request: BranchRequest): BranchResponse

    suspend fun getById(id: UUID): BranchResponse

    suspend fun getByShortName(shortName: String): BranchResponse

    suspend fun update(id: UUID, request: BranchRequest)

    suspend fun getAll(): List<BranchResponse>
    suspend fun delete(id: UUID)
}

class BranchRepositoryImpl : BranchRepository {

    private val logger = KtorSimpleLogger(BranchRepositoryImpl::class.jvmName)

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
        result.resultedValues?.singleOrNull()?.let(::toBranchResponse)
            ?: throw DatabaseException("create branch failed")
    }

    override suspend fun getById(id: UUID): BranchResponse = dbQuery {
        Branches
            .select { Branches.id eq id }
            .map(::toBranchResponse)
            .singleOrNull() ?: throw BranchNotFoundException(id)
    }

    override suspend fun getByShortName(shortName: String): BranchResponse = dbQuery {
        Branches
            .select { Branches.shortName eq shortName }
            .map(::toBranchResponse)
            .singleOrNull() ?: throw BranchNotFoundException(shortName)
    }

    override suspend fun update(id: UUID, request: BranchRequest) = dbQuery {
        val update = Branches.update({ Branches.id eq id }) {
            it[shortName] = request.shortName
            it[name] = request.name
            it[updatedAt] = LocalDateTime.now()
        }

        logger.info("rows updated: $update")
        if (update == 0) {
            throw BranchNotFoundException(id)
        }
    }

    override suspend fun getAll(): List<BranchResponse> = dbQuery {
        Branches.selectAll().map(::toBranchResponse)
    }

    override suspend fun delete(id: UUID) = dbQuery {
        val deleted = Branches.deleteWhere { Branches.id eq id }
        if (deleted != 1) {
            throw BranchNotFoundException(id)
        }
    }
}