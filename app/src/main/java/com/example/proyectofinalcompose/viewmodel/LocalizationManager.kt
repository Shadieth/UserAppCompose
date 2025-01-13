package com.example.proyectofinalcompose.viewmodel

import android.content.Context
import org.json.JSONObject
import java.nio.charset.Charset

object LocalizationManager {
    private lateinit var strings: JSONObject

    fun loadStrings(context: Context, languageCode: String) {
        val inputStream = context.assets.open("strings.json")
        val json = inputStream.readBytes().toString(Charset.defaultCharset())
        val jsonObject = JSONObject(json)
        strings = jsonObject.getJSONObject(languageCode)
    }

    fun getString(key: String): String {
        return strings.optString(key, "Missing text for key: $key")
    }
}
