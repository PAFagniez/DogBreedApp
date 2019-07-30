package com.paf.dogbreedapp.app.di.module

import com.paf.dogbreedapp.Environment
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource
import com.paf.dogbreedapp.data.common.ConverterFactory
import com.paf.dogbreedapp.data.repository.DogBreedRepository
import com.paf.dogbreedapp.domain.service.DogBreedService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton


@Module
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideDogBreedRepository(dogBreedService: DogBreedService): DogBreedDataSource =
        DogBreedRepository(dogBreedService)


    @Provides
    @Singleton
    internal fun provideDogBreedService(retrofit: Retrofit): DogBreedService =
        retrofit.create(DogBreedService::class.java)


    @Provides
    @Singleton
    internal fun providerDogBreedApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Environment.BASE_DOG_BREED_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ConverterFactory())
            .build()
    }
}

