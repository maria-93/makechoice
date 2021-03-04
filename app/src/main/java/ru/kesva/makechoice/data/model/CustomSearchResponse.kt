package ru.kesva.makechoice.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.kesva.makechoice.domain.model.Items

@JsonClass(generateAdapter = true)
open class CustomSearchResponse (
    @field:Json(name = "items") val items: List<Items>
)