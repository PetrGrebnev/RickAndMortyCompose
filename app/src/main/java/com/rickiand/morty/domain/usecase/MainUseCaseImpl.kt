package com.rickiand.morty.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rickiand.morty.data.model.DetailPersonDto
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.data.repository.MainRepository
import com.rickiand.morty.data.utils.onError
import com.rickiand.morty.data.utils.onSuccess
import com.rickiand.morty.domain.model.ListPersonDomain
import com.rickiand.morty.domain.model.LocationDomain
import com.rickiand.morty.domain.model.PersonDomain
import com.rickiand.morty.domain.utils.DomainResult
import com.rickiand.morty.domain.utils.MyPagingSource
import com.rickiand.morty.domain.utils.ResultWrapper
import com.rickiand.morty.domain.utils.ResultWrapperImpl
import com.rickiand.morty.domain.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository,
) : MainUseCase, ResultWrapper by ResultWrapperImpl() {
    override val personFlow: Flow<ListPersonDomain>
        get() = _flow

    private val _flow = MutableSharedFlow<ListPersonDomain>()
    override val locationFlow: Flow<List<LocationDomain>>
        get() = _locationFlow

    private val _locationFlow = MutableSharedFlow<List<LocationDomain>>()

    override fun getListPerson(): Flow<PagingData<PersonDto>> =
        Pager(
            config = PagingConfig(40),
            pagingSourceFactory = {
                MyPagingSource(
                    repository = mainRepository
                )
            }
        ).flow


    override suspend fun getLocation(): DomainResult<Unit> =
        wrap(
            block = { mainRepository.getListLocation() },
            mapper = { result ->
                val locationList = mutableListOf<LocationDomain>()
                result.results.forEach { locationADto ->
                    val personList = mutableListOf<DetailPersonDto>()
                    for (i in 0 until locationADto.urlPerson.size) {
                        if (i <= 5) {
                            wrap(
                                block = { mainRepository.getPerson(locationADto.urlPerson[i]) },
                                mapper = { personList.add(it) }
                            )
                        } else {
                            break
                        }
                    }
                    locationList.add(locationADto.toDomain(personList))
                }
                _locationFlow.emit(locationList)
            }
        )

    override suspend fun getPerson(url: String): DomainResult<PersonDomain> =
        wrap(
            block = { mainRepository.getPerson(url = url) },
            mapper = {
                PersonDomain(id = it.id, name = it.name, avatar = it.image)
            }
        )
}