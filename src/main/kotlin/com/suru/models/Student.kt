package com.suru.models

import com.suru.plugins.Serialization
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

enum class AcademicYear {
    I, II, III, IV
}

data class Student(
    val id: UUID,
    val name: String,
    val branch: UUID,
    val year: AcademicYear,
    val dob: LocalDate,
    val email: String,
    val phone: String,
)

object Students : Table("student") {
    val id = uuid("s_id")
    val name = varchar("s_name", 100)
    val branch = uuid("s_branch").references(Branches.id)
    val year = enumeration("s_year", AcademicYear::class)
    val dob = date("s_dob")
    val email = varchar("s_email", 100)
    val phone = varchar("s_phone", 20)

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class StudentRequest(
    val name: String,
    @Serializable(with = Serialization.UUIDSerializer::class)
    val branch: UUID,
    val year: AcademicYear,
    @Serializable(with = Serialization.DateSerializer::class)
    val dob: LocalDate,
    val email: String,
    val phone: String,
)

@Serializable
data class StudentResponse(
    @Serializable(with = Serialization.UUIDSerializer::class)
    val id: UUID,
    val name: String,
    @Serializable(with = Serialization.UUIDSerializer::class)
    val branch: UUID,
    val year: Int,
    @Serializable(with = Serialization.DateSerializer::class)
    val dob: LocalDate,
    val email: String,
    val phone: String,
    @Serializable(with = Serialization.DateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = Serialization.DateTimeSerializer::class)
    val updatedAt: LocalDateTime
)