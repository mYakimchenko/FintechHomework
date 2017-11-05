package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gson = GsonBuilder()
                .registerTypeAdapter(Second::class.java, CustomTypeAdapter())
                .create()
        val second = gson.fromJson("""{"name":"name","any_map":{"a":"55","b":"85","c":"56"}}""",
                Second::class.java)
        textView.text = second.toString()
        Log.d(javaClass.name, gson.toJson(second))
    }
}

class CustomTypeAdapter : TypeAdapter<Second>() {
    override fun read(input: JsonReader): Second {
        var name = ""
        val map = HashMap<String, Int>()
        input.beginObject()
        while (input.hasNext()) {
            when (input.nextName()) {
                "name" -> name = input.nextString()
                "any_map" -> {
                    input.beginObject()
                    while (input.hasNext()) {
                        map.put(input.nextName(), input.nextInt())
                    }
                    input.endObject()
                }
            }
        }
        input.endObject()
        return Second(name, map)
    }

    override fun write(out: JsonWriter, second: Second) {
        out.beginObject()
        out.name("name").value(second.name)
        out.name("any_map").beginObject()
        second.any_map.forEach { out.name(it.key).value(it.value) }
        out.endObject()
        out.endObject()
    }
}
