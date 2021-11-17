package com.test.tix.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.model.UsersResponse
import com.test.tix.core.app.repository.TixRepository
import com.test.tix.core.app.source.RemoteDataSource
import com.test.tix.core.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: TixRepository) : ViewModel() {

    private val searchQuery = MutableLiveData<String>()
    private var searchedUser = Transformations.switchMap(searchQuery) {
        repo.searchUser(it)
    } as MutableLiveData<Resource<UsersResponse>>

    private var searchedUserPaged = Transformations.switchMap(searchQuery) {
        repo.searchUser(it)
    } as MutableLiveData<Resource<UsersResponse>>


    fun setSearch(query: String) = viewModelScope.launch {
        searchQuery.postValue(query)
    }

    fun getListData(): Flow<PagingData<ItemUser>> {
        return Pager (config = PagingConfig(pageSize = 10, maxSize = 200),
            pagingSourceFactory = {RemoteDataSource()}).flow.cachedIn(viewModelScope)
    }

    fun getSearched() = searchedUser

}