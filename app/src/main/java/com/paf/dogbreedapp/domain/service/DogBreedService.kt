package com.paf.dogbreedapp.domain.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedService {

    @GET("breeds/list/all")
    fun getAllBreeds(): Call<String>

    @GET("breed/{breed}/images")
    fun getBreedImages(@Path("breed") breed: String): Call<String>

    @GET("breed/{breed}/{sub_breed}/images")
    fun getSubBreedImages(@Path("breed") breed: String,
                          @Path("sub_breed") subBreed: String): Call<String>
}