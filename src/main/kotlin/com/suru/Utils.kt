package com.suru

import com.suru.plugins.BadRequestException
import java.util.UUID

object Utils {
    fun toUUID(str: String?): UUID {
        return try {
            UUID.fromString(str)
        } catch (e: Exception) {
            throw BadRequestException("invalid id")
        }
    }
}