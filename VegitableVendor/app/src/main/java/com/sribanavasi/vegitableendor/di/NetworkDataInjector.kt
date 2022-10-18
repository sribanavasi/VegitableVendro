package com.sribanavasi.vegitableendor.di

import com.sribanavasi.vegitableendor.network.RetrofitInterface
import com.sribanavasi.vegitableendor.repository.NetworkRepository
import com.sribanavasi.vegitableendor.repository.NetworkRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkDataInjector {
    @Singleton
    @Provides
    @Named("BASE_URL")
    fun baseUrl() =
        "https://script.google.com/macros/s/AKfycbwwAN9v52ZtG7b2Q8qMVxly5gyAoGIHGOGIIgU5YtZBaULpQgcrfsk9gN9Xw6I-sDEI/"

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    }

    @Singleton
    @Provides
    @Named("RETROFIT")
    fun retrofit(@Named("BASE_URL") baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun retrofitInstance(@Named("RETROFIT") retrofit: Retrofit): RetrofitInterface {
        return retrofit.create(RetrofitInterface::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun networkRepository(repo: NetworkRepositoryImp): NetworkRepository

}