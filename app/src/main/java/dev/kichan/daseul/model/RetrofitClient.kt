package dev.kichan.daseul.model

import dev.kichan.daseul.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)

    fun getRetrofit(): Retrofit {
        retrofitBuilder.client(clientBuilder.build())
        return retrofitBuilder.build()
    }
}