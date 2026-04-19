package com.salem.base.ui.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salem.base.R
import com.salem.domain.entity.User
import com.salem.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    var id: Int = 101

    val genders = listOf(R.string.male, R.string.female)
    var oldUsersList = mutableListOf<User>()
    var newUsersList = mutableListOf<User>()

    private val _navigateToHome = Channel<Unit>(Channel.BUFFERED)
    val navigateToHome = _navigateToHome.receiveAsFlow()

    private val _validationErrors = Channel<RegistrationValidation>(Channel.BUFFERED)
    val validationErrors = _validationErrors.receiveAsFlow()

    fun register(
        name: String,
        age: String,
        jobTitle: String,
        gender: String,
    ) {
        viewModelScope.launch {
            val validation = RegistrationValidation(
                nameError = if (name.isEmpty()) R.string.please_enter_name else null,
                ageError = if (age.isEmpty()) R.string.please_enter_age else null,
                jobError = if (jobTitle.isEmpty()) R.string.please_enter_job_title else null,
                genderError = if (gender.isEmpty()) R.string.please_enter_gender else null,
            )

            _validationErrors.send(validation)

            if (!validation.isValid) return@launch

            id += 1
            val existingUsers = preferenceRepository.getUsersData().first()
            oldUsersList.clear()
            oldUsersList.addAll(existingUsers)
            oldUsersList.add(User(id = id, name = name, age = age, job = jobTitle, gender = gender))
            newUsersList.clear()
            newUsersList.addAll(oldUsersList)
            saveUsersData(newUsersList)
            _navigateToHome.send(Unit)
        }
    }

    suspend fun saveUsersData(usersData: MutableList<User>) {
        viewModelScope.async {
            preferenceRepository.setUsersData(usersData)
        }.await()
    }
}