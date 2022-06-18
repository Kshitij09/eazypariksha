package com.kk.eazypariksha.data

import com.kk.eazypariksha.model.exam.ExamRequest

interface ExamRepository {
    suspend fun create(exam: ExamRequest)
}