package com.aait.domain.entity

enum class Gender {
    MALE,
    FEMALE;

    fun getGenderId() = this.ordinal + 1
}
