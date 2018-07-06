package com.hieupham.data.util

import android.content.res.Resources
import java.io.File
import java.lang.reflect.ParameterizedType

class TestUtil {

    companion object {
        fun getResponse(name: String): String? {
            val path = "src/test/java/com/hieupham/data/data/response/$name"
            val file = File(path)
            return if (file.exists()) file.readText() else throw Resources.NotFoundException(
                    "The response file with path $path is not found")
        }

        inline fun <reified T, R> getGenericTypeClass(instance: Any): Class<R> {
            var type = instance.javaClass.genericSuperclass
            while (type !is ParameterizedType || type.rawType !== T::class.java) {
                type = if (type is ParameterizedType) {
                    (type.rawType as Class<*>).genericSuperclass
                } else {
                    (type as Class<*>).genericSuperclass
                }
            }

            return type.actualTypeArguments[0] as Class<R>
        }
    }
}