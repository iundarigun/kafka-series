package br.com.devcave.alura.kafka.utils

import com.google.gson.GsonBuilder
import org.apache.kafka.common.serialization.Serializer

class GsonSerializer<T> : Serializer<T> {

    private val gson = GsonBuilder().create()

    override fun serialize(topic: String?, data: T): ByteArray {
        return gson.toJson(data).toByteArray()
    }
}
