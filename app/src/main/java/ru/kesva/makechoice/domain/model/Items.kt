package ru.kesva.makechoice.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items (
    @field:Json(name = "image") val image: Image
)