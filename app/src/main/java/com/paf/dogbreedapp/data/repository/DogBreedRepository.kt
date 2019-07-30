package com.paf.dogbreedapp.data.repository

import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.core.model.datasource.DogBreedDataSource
import com.paf.dogbreedapp.data.common.JSON
import com.paf.dogbreedapp.data.parser.DogBreedListParser
import com.paf.dogbreedapp.data.parser.DogBreedImageParser
import com.paf.dogbreedapp.domain.service.DogBreedService
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class DogBreedRepository(val service: DogBreedService): DogBreedDataSource {

    override fun getAllDogBreeds(): Observable<List<DogBreed>> {
        return Observable.create { emitter ->

            service.getAllBreeds().enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val json = JSON(response.body())
                    emitter.onNext(DogBreedListParser().parse(json))
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }

    override fun getImagesForBreed(dogBreed: DogBreed): Observable<DogBreed> {
        return Observable.create { emitter ->
            if(dogBreed.subBreed == null)
                getBreedImages(dogBreed, emitter)
            else
                getSubBreedImages(dogBreed, emitter)
        }
    }

    private fun getBreedImages(dogBreed: DogBreed, emitter: ObservableEmitter<DogBreed>) {
        service.getBreedImages(dogBreed.breed).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = JSON(response.body())
                emitter.onNext(DogBreedImageParser(dogBreed).parse(json))
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                emitter.onError(t)
            }
        })
    }

    private fun getSubBreedImages(dogBreed: DogBreed, emitter: ObservableEmitter<DogBreed>) {
        dogBreed.subBreed?.let {
            service.getSubBreedImages(dogBreed.breed, it).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    val json = JSON(response.body())
                    emitter.onNext(DogBreedImageParser(dogBreed).parse(json))
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}