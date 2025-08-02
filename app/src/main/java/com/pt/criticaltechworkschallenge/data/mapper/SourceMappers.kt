package com.pt.criticaltechworkschallenge.data.mapper

import com.pt.criticaltechworkschallenge.data.remote.dto.SourceDto
import com.pt.criticaltechworkschallenge.domain.model.Source

fun SourceDto.toDomain(): Source {
    return Source(
        id = id ?: "",
        name = name
    )
}