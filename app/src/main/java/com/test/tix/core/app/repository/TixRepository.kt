package com.test.tix.core.app.repository

import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.source.RemoteDataSource

class TixRepository(
    private val remoteDataSource: RemoteDataSource
) {

    fun searchUser(query: String) = remoteDataSource.searchUser(query)
    fun getRepoUser(user: String?) = remoteDataSource.getRepoUser(user)
    fun getDetailUser(itemUser: ItemUser) = remoteDataSource.getDetailUser(itemUser.login)
}