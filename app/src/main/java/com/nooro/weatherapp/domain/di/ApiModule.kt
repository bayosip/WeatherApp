package com.nooro.weatherapp.domain.di

import android.util.Log
import com.nooro.weatherapp.domain.local.LocalService
import com.nooro.weatherapp.domain.model.DataToUIMapper
import com.nooro.weatherapp.domain.network.ApiService
import com.nooro.weatherapp.repository.Repository
import com.nooro.weatherapp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    const val TOME_OUT: Long = 30

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.i("TAG", "providesHttpLoggingInterceptor: $message")
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url = originalHttpUrl.newBuilder().addQueryParameter(
                    "key",
                    "c26c99604623486cae5130818241312"
                ).build()
                request.url(url)
                val response = chain.proceed(request.build())
                return@addInterceptor response
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService, localService: LocalService, mapper: DataToUIMapper): Repository =
        RepositoryImpl(apiService, mapper, localService)

    @Singleton
    @Provides
    fun provideDataMapper(): DataToUIMapper = DataToUIMapper()
}