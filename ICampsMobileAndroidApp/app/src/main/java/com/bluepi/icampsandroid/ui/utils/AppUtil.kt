package com.bluepi.icampsandroid.ui.utils

import android.content.Context
import java.io.IOException


object AppUtil {
    /**
     * Get the json data from json file.
     *
     * @param context  the context to acces the resources.
     * @param fileName the name of the json file
     * @return json as string
     */
    fun getJsonFromAsset(context: Context, fileName: String): String {
        var jsonString = ""
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }
}