package ru.kesva.makechoice.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.kesva.makechoice.domain.model.Photo

@JsonClass(generateAdapter = true)
open class PexelsResponse(
    @field:Json(name = "photos") val photos: List<Photo>
)