package com.test.tix.detail

import androidx.lifecycle.*
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.model.ReposResponseItem
import com.test.tix.core.app.repository.TixRepository
import com.test.tix.core.app.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: TixRepository, private val itemUser: ItemUser) : ViewModel() {

    private lateinit var detail: LiveData<Resource<ItemUser>>
    private lateinit var repos:  LiveData<Resource<MutableList<ReposResponseItem>>>

    init {
        initDetailUser()
        initRepos()
    }

    fun initRepos() = viewModelScope.launch {
        repos = repo.getRepoUser(itemUser.login)
    }


    fun initDetailUser() = viewModelScope.launch {
        detail = repo.getDetailUser(itemUser)
    }

    fun getDetailUser() = detail
    fun getRepository() = repos


}