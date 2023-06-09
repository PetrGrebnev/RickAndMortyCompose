package com.rickiand.morty.domain.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickiand.morty.data.model.PersonDto
import com.rickiand.morty.data.repository.MainRepository
import com.rickiand.morty.data.utils.onSuccess

class MyPagingSource(
    private val repository: MainRepository
) : PagingSource<Int, PersonDto>() {

    override fun getRefreshKey(state: PagingState<Int, PersonDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonDto> {
        val position = params.key ?: 1
        var nextKey: Int? = null
        var prevKey: Int? = null
        var list = listOf<PersonDto>()
        val response = repository.getListPerson(position)
        response.onSuccess {
            nextKey = if (it.info.nextPage != null) position + 1 else null
            prevKey = if (it.info.prevPage!= null) position - 1 else null
            list = it.results
        }
        return LoadResult.Page(
            data = list,
            nextKey = nextKey,
            prevKey = prevKey
        )
    }
}