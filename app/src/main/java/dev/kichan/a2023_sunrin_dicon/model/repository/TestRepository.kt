package dev.kichan.a2023_sunrin_dicon.model.repository

import dev.kichan.a2023_sunrin_dicon.model.RetrofitClient
import dev.kichan.a2023_sunrin_dicon.model.data.test.TestReq
import dev.kichan.a2023_sunrin_dicon.model.service.TestService

class TestRepository {
    private val retrofit = RetrofitClient.getRetrofit().create(TestService::class.java)

    suspend fun test(testReq: TestReq) {
        retrofit.testLocation(testReq)
    }
}