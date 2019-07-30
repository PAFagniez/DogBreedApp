package com.paf.dogbreedapp.data.parser

import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.data.common.JSON
import com.paf.dogbreedapp.data.common.JSONParser
import com.paf.dogbreedapp.data.common.invoke
import com.paf.dogbreedapp.data.common.jsonArrayOrNull
import org.json.JSONArray

class DogBreedListParser: JSONParser<List<DogBreed>> {

    override fun parse(json: JSON): List<DogBreed> {
        val result = json("message") ?: json

        val dogBreedList = ArrayList<DogBreed>()

        result.jsonObject.keys().forEach { breedName ->
            val jsonBreed = result.jsonArrayOrNull(breedName)
            jsonBreed?.let {

                if(it.length() < 1){
                    dogBreedList.add(DogBreed(breedName))
                }
                else {
                    addSubBreedsToList(it, breedName, dogBreedList)
                }
            }
        }
        return dogBreedList
    }

    private fun addSubBreedsToList(breed: JSONArray, breedName: String, breedList: ArrayList<DogBreed>) {
        for (i in 0 until breed.length()) {
            val subBreed = DogBreed(breedName, breed.getString(i))
            breedList.add(subBreed)
        }
    }
}