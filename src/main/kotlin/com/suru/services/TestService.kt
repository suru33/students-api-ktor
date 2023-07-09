package com.suru.services

import com.suru.repositories.TestRepository

class TestService(private val testRepository: TestRepository) {
    fun greetUser(name: String): String {
        return "${testRepository.sayHello()}, $name"
    }
}