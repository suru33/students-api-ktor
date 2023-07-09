package com.suru.services

import com.suru.models.BranchRequest
import com.suru.repositories.BranchRepository
import java.util.UUID

class BranchService(private val repository: BranchRepository) {
    suspend fun create(request: BranchRequest) = repository.create(request)

    suspend fun getById(id: UUID) = repository.getById(id)

    suspend fun getByShortName(shortName: String) = repository.getByShortName(shortName)

    suspend fun update(id: UUID, request: BranchRequest) = repository.update(id, request)

    suspend fun getAll() = repository.getAll()

    suspend fun delete(id: UUID) = repository.delete(id)
}