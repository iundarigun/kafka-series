package br.com.devcave.alura.kafka.utils

import com.google.gson.GsonBuilder
import org.apache.kafka.common.serialization.Deserializer
import java.nio.charset.Charset

class GsonDeserializer<T> : Deserializer<T> {

    companion object{
        const val TYPE_CONFIG = "br.com.devcave.alura.type_config"
    }

    private lateinit var type: Class<T>
    private val gson = GsonBuilder().create()

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        val typeName = configs?.getOrDefault(TYPE_CONFIG, "").toString()
        type = Class.forName(typeName) as Class<T>
    }

    override fun deserialize(topic: String?, data: ByteArray?): T? {
        return data?.let {
            gson.fromJson(it.toString(Charset.defaultCharset()), type)
        }
    }
}
