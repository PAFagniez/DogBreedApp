package com.paf.dogbreedapp

import com.paf.dogbreedapp.core.model.DogBreed
import com.paf.dogbreedapp.data.common.JSON
import com.paf.dogbreedapp.data.parser.DogBreedListParser
import com.paf.dogbreedapp.utils.getJsonFromJsonFile
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DogBreedListParserTest {

    private val expectedList = ArrayList<DogBreed>()
    private lateinit var goodJson: JSON

    @Before
    fun setUp(){
        goodJson = getJsonFromJsonFile("DogBreedListJson")

        initExpectedList()
    }

    @Test
    fun testSucceedParsingJson() {
        val actualList = DogBreedListParser().parse(goodJson)

        actualList.forEachIndexed { i, dogBreed ->
            assertEquals("the dog $i isn't equals to the expected one", expectedList[i], dogBreed)
        }
    }

    private fun initExpectedList(){
        expectedList.apply {
            add(DogBreed("bulldog","boston"))
            add(DogBreed("bulldog", "english"))
            add(DogBreed("bulldog", "french"))
            add(DogBreed("briard"))
        }
    }
}