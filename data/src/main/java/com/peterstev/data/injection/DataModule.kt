package com.peterstev.data.injection

import android.app.Application
import com.peterstev.data.api.ApiService
import com.peterstev.data.interactors.BaseCaseImpl
import com.peterstev.data.interactors.DetailCaseImpl
import com.peterstev.data.utilities.BASE_URL
import com.peterstev.domain.usecases.BaseUseCases
import com.peterstev.domain.usecases.DetailUseCases
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesOkHTTPClient(application: Application): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                ChuckInterceptor(application)
                    .showNotification(true)
                    .retainDataFor(ChuckInterceptor.Period.ONE_DAY)
            )
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesBaseUseCase(baseCaseImpl: BaseCaseImpl): BaseUseCases = baseCaseImpl

    @Provides
    @Singleton
    fun providesDetailUseCase(detailCaseImpl: DetailCaseImpl): DetailUseCases = detailCaseImpl
}