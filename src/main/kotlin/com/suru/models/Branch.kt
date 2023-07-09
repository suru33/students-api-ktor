package com.suru.models

import com.suru.plugins.Serialization
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

data class Branch(
    val id: UUID,
    val shortName: String,
    val name: String,
)

object Branches : Table("branch") {
    val id = uuid("b_id")
    val shortName = varchar("b_short_name", 10)
    val name = varchar("b_name", 100)
    val createdAt = timestamp("b_created_at")
    val updatedAt = timestamp("b_updated_at")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class BranchRequest(
    val shortName: String,
    val name: String
)

@Serializable
data class BranchResponse(
    @Serializable(with = Serialization.UUIDSerializer::class)
    val id: UUID,
    val shortName: String,
    val name: String,
    @Serializable(with = Serialization.DateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = Serialization.DateTimeSerializer::class)
    val updatedAt: LocalDateTime
)