package com.kk.eazypariksha.di

import com.kk.eazypariksha.data.ExamRepository
import com.kk.eazypariksha.data.InMemoryRepository

object RepositoryModule {
    fun provideExamRepository(): ExamRepository = InMemoryRepository
}