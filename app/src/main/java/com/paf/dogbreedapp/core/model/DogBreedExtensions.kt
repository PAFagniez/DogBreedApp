package com.paf.dogbreedapp.core.model

fun DogBreed.getName(): String {
    return if(this.subBreed != null) subBreed.capitalize() + "  "+ breed.capitalize()
    else breed.capitalize()
}