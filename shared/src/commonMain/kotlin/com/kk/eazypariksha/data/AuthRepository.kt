package com.kk.eazypariksha.data

interface AuthRepository {
    suspend fun teacherLogin(username: String, password: String): Boolean
}