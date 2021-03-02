package ru.kesva.makechoice.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @field:Json(name = "src") val source: ImageSource
)