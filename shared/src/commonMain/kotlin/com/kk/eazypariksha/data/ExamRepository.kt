package com.kk.eazypariksha.data

import com.kk.eazypariksha.model.exam.Exam
import com.kk.eazypariksha.model.exam.ExamRequest
import com.kk.eazypariksha.model.exam.Subject

interface ExamRepository {
    suspend fun create(examRequest: ExamRequest)
    suspend fun saveDraft(examRequest: ExamRequest)
    suspend fun getOngoing(): List<Exam>
    suspend fun getDrafts(): List<Exam>
    suspend fun getAllSubjects(): List<Subject>
}