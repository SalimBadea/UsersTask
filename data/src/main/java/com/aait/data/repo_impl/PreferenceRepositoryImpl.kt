package com.aait.data.repo_impl

import com.aait.data.datasource.PreferenceDataSource
import com.aait.data.util.PreferenceConstants
import com.aait.domain.entity.User
import com.aait.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(private val preferenceDataSource: PreferenceDataSource) :
    PreferenceRepository {


    override suspend fun getUserData() = flow {
        preferenceDataSource.getValue(PreferenceConstants.USER_DATA, User).collect {
            emit(it as User)
        }
    }

    override suspend fun setUserData(userData: User) {
        preferenceDataSource.setValue(PreferenceConstants.USER_DATA, userData)
    }

    override suspend fun getUsersData() = flow {
        preferenceDataSource.getValue(PreferenceConstants.USERS_DATA, "").collect { raw ->
            val json = raw as? String
            val list = if (json.isNullOrEmpty()) mutableListOf()
            else Json.decodeFromString<MutableList<User>>(json)
            emit(list)
        }
    }

    override suspend fun setUsersData(usersData: MutableList<User>) {
        val json = Json.encodeToString(usersData)
        preferenceDataSource.setValue(PreferenceConstants.USERS_DATA, json)
    }

}