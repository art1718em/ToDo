package com.example.todo.ui.appInformationScreen

import android.content.Context
import com.example.todo.di.app.AppScope
import org.json.JSONObject
import javax.inject.Inject

class AssetsReader(private val context: Context) {

    fun read(filename : String): JSONObject {
        val data = IOUtils.toString(context.assets.open(filename))
        return JSONObject(data)
    }
}