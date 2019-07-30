package com.paf.dogbreedapp.core.model.datasource

import com.paf.dogbreedapp.core.model.DogBreed
import io.reactivex.Observable

interface DogBreedDataSource {
    fun getAllDogBreeds(): Observable<List<DogBreed>>
    fun getImagesForBreed(dogBreed: DogBreed): Observable<DogBreed>
}