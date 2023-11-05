package dev.kichan.daseul.model.service

import dev.kichan.daseul.model.data.test.TestReq
import dev.kichan.daseul.model.data.test.TestRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TestService {
    @POST("ingest/location")
    suspend fun testLocation(@Body body : TestReq) : Response<TestRes>
}