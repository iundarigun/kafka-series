package br.com.devcave.kafka.springkafka.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.time.LocalDate

data class Employee(
    @JsonProperty("name")
    val name:String,
    @get:JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @JsonProperty("admissionDate")
    val admissionDate: LocalDate,
    @JsonProperty("email")
    val email: String){
}