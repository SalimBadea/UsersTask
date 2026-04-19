package com.aait.base.ui.auth.registration

data class RegistrationValidation(
    val nameError: Int? = null,
    val ageError: Int? = null,
    val jobError: Int? = null,
    val genderError: Int? = null,
) {
    val isValid = nameError == null && ageError == null && jobError == null && genderError == null
}
