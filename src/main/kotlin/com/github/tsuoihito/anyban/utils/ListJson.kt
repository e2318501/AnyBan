package com.github.tsuoihito.anyban.utils

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException

fun <T> loadList(objectClass: Class<T>, mapper: ObjectMapper, parent: File, jsonFileName: String): List<T> {
    val collectionType = mapper.typeFactory.constructCollectionType(List::class.java, objectClass)
    val jsonFile = File(parent, jsonFileName)
    try {
        parent.mkdir()
        if (jsonFile.exists()) {
            return mapper.readValue(jsonFile, collectionType)
        }
        return listOf()
    } catch (e: IOException) {
        e.printStackTrace()
        return listOf()
    }
}

fun <T> saveList(mapper: ObjectMapper, parent: File, jsonFileName: String, objectList: List<T>) {
    val jsonFile = File(parent, jsonFileName)
    try {
        mapper.writeValue(jsonFile, objectList)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
