package com.aait.base.ui.home.container.main_first

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aait.domain.entity.User
import com.aait.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFirstViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        viewModelScope.launch {
            preferenceRepository.getUsersData().collect { list ->
                _users.value = list
            }
        }
    }
}
