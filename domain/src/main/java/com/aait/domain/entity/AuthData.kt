package com.aait.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("age") val age: String? = null,
    @SerialName("job") val job: String? = null,
    @SerialName("gender") val gender: String? = null,
)