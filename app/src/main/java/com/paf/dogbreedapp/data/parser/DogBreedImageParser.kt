package com.paf.dogbreedapp.data.parser

import android.net.Uri
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.data.common.JSON
import com.paf.dogbreedapp.data.common.JSONParser
import com.paf.dogbreedapp.data.common.jsonArrayOrNull

class DogBreedImageParser(val dogBreed: DogBreed): JSONParser<DogBreed> {

    override fun parse(json: JSON): DogBreed {
        val imageListJson = json.jsonArrayOrNull("message")

        val imageList = ArrayList<Uri>()
        if (imageListJson != null) {

            for (i in 0 until imageListJson.length()) {
                val imageUri = imageListJson.getString(i)
                imageList.add(Uri.parse(imageUri))
            }
        }

        dogBreed.imageList = imageList
        return dogBreed
    }
}