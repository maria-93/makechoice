package ru.kesva.makechoice.data.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ModelSerializationTest {

    private val converter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()



    @Test
    fun jsonToPhotoResponseTest_stringCreatedCorrectly() {
        val cardAdapter = converter.adapter(PhotoResponse::class.java)
        val photoResponse = cardAdapter.fromJson(JsonTestStrings.responseJsonString)!!
        assertThat(photoResponse.items.last().image.imageUri).isEqualTo(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv7jgdjvvfDihabC7KdMMo7F5BG892do3BbKkCFCgfKEA1s7usfi8PZA&s"
        )

    }
}