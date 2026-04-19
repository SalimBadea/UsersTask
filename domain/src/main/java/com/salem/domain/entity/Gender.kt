package com.salem.domain.entity

enum class Gender {
    MALE,
    FEMALE;

    fun getGenderId() = this.ordinal + 1
}
