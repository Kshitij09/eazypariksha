package com.kk.eazypariksha.data

import com.kk.eazypariksha.model.Exam

interface ExamRepository {
    suspend fun create(exam: Exam)
}