package dev.kichan.a2023_sunrin_dicon.model.service

import dev.kichan.a2023_sunrin_dicon.model.data.test.TestReq
import dev.kichan.a2023_sunrin_dicon.model.data.test.TestRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TestService {
    @POST("/v1/ingest/location")
    suspend fun testLocation(@Body body : TestReq) : Response<TestRes>
}