package com.suru.services

import com.suru.models.BranchRequest
import com.suru.repositories.BranchRepository

class BranchService(private val repository: BranchRepository) {
    suspend fun create(request: BranchRequest) = repository.create(request)
}