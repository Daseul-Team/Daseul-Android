package dev.kichan.daseul.model.repository

import dev.kichan.daseul.model.RetrofitClient
import dev.kichan.daseul.model.data.test.TestReq
import dev.kichan.daseul.model.service.TestService

class TestRepository {
    private val retrofit = RetrofitClient.getRetrofit().create(TestService::class.java)

    suspend fun test(testReq: TestReq) {
        retrofit.testLocation(testReq)
    }
}