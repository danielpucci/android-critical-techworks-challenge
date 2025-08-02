package com.pt.criticaltechworkschallenge.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceDto(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "name") val name: String
)