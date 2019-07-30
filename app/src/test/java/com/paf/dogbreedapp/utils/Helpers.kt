package com.paf.dogbreedapp.utils

import com.paf.dogbreedapp.data.common.JSON
import java.io.File

fun Any.getJsonFromJsonFile(name: String): JSON {

    val path = javaClass.classLoader!!.getResource(name)
    val file = File(path.toURI())
    val jsonString = file.readText()
    return JSON(jsonString)
}