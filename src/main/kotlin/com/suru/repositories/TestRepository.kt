package com.suru.repositories

interface TestRepository {
    fun sayHello(): String
}

class TestRepositoryImpl : TestRepository {
    override fun sayHello() = "Hello"
}