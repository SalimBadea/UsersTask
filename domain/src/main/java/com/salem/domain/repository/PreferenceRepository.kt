package com.salem.domain.repository

import com.salem.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun getUserData(): Flow<User>
    suspend fun setUserData(userData: User)

    suspend fun getUsersData(): Flow<MutableList<User>>
    suspend fun setUsersData(usersData: MutableList<User>)

}