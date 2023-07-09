package com.suru.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

object Serialization {
    object UUIDSerializer : KSerializer<UUID> {
        override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): UUID {
            return UUID.fromString(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: UUID) {
            encoder.encodeString(value.toString())
        }
    }

    object DateTimeSerializer : KSerializer<LocalDateTime> {
        override val descriptor = PrimitiveSerialDescriptor("DateTime", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): LocalDateTime {
            return LocalDateTime.parse(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            return encoder.encodeString(value.toString())
        }
    }

    object DateSerializer : KSerializer<LocalDate> {
        override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): LocalDate {
            return LocalDate.parse(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: LocalDate) {
            return encoder.encodeString(value.toString())
        }
    }
}
