package com.github.tsuoihito.anyban.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException

inline fun <reified T> loadList(mapper: ObjectMapper, parent: File, jsonFileName: String): List<T> {
    val jsonFile = File(parent, jsonFileName)
    try {
        parent.mkdir()
        if (jsonFile.exists()) {
            return mapper.readValue(jsonFile, object : TypeReference<List<T>>() {})
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
