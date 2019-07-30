package com.paf.dogbreedapp

import android.net.Uri
import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.data.common.JSON
import com.paf.dogbreedapp.data.parser.DogBreedImageParser
import com.paf.dogbreedapp.utils.getJsonFromJsonFile
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DogBreedImageParserTest {

    private val dogBreed = DogBreed("bulldog")
    private lateinit var goodJson: JSON

    @Before
    fun setUp(){
        goodJson = getJsonFromJsonFile("DogImageListJson")
    }

    @Test
    fun testSucceedParsingJson() {
        val actualDogBreed = DogBreedImageParser(dogBreed).parse(goodJson)

        val expectedDogBreed = dogBreed
        expectedDogBreed.imageList = listOf(
            Uri.parse("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"),
            Uri.parse("https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg")
        )

        assertEquals("the url at position 0 isn't equals to the expected one",
            expectedDogBreed.imageList!![0], actualDogBreed.imageList!![0])
        assertEquals("the url at position 1 isn't equals to the expected one",
            expectedDogBreed.imageList!![1], actualDogBreed.imageList!![1])

    }
}