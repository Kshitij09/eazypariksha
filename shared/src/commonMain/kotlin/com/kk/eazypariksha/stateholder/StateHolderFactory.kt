package com.kk.eazypariksha.stateholder

import com.kk.eazypariksha.di.RepositoryModule
import com.kk.eazypariksha.stateholder.addexam.AddExamStateHolder
import com.kk.eazypariksha.stateholder.addexam.RealAddExamStateHolder
import kotlinx.coroutines.CoroutineScope

object StateHolderFactory {
    fun provideAddExamStateHolder(coroutineScope: CoroutineScope): AddExamStateHolder {
        val repository = RepositoryModule.provideExamRepository()
        return RealAddExamStateHolder(coroutineScope, repository)
    }
}