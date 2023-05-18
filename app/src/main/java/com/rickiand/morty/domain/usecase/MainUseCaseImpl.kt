package com.rickiand.morty.domain.usecase

import com.rickiand.morty.data.repository.MainRepository
import com.rickiand.morty.domain.model.ListPersonDomain
import com.rickiand.morty.domain.utils.DomainResult
import com.rickiand.morty.domain.utils.ResultWrapper
import com.rickiand.morty.domain.utils.ResultWrapperImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
) : MainUseCase, ResultWrapper by ResultWrapperImpl() {
    override val flow: Flow<ListPersonDomain>
        get() = _flow
    private val _flow = MutableSharedFlow<ListPersonDomain>()

    override suspend fun getListPerson(): DomainResult<Unit> =
        wrap(
            block = { mainRepository.getListPerson() },
            mapper = {
                _flow.emit(ListPersonDomain(it.results))
            }
        )

}