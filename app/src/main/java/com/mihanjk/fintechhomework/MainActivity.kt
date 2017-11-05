package com.mihanjk.fintechhomework

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val exclusionStrategy = SomeExclusionStrategy(listOf("isFavorite", "id"), listOf(Long::class.java))
        val gson = GsonBuilder()
                .addSerializationExclusionStrategy(exclusionStrategy)
                /*
                or we can just use @Expose annotation in data class properties, which needed to
                serialize, and invoke this method on GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                 */
                .create()
        textView.text = gson.toJson(First(21, "name", false, 200003))
    }
}

class SomeExclusionStrategy(val fieldNames: List<String>, val classes: List<Class<*>>) : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean = classes.contains(clazz)

    override fun shouldSkipField(f: FieldAttributes?): Boolean = fieldNames.contains(f?.name)
}
