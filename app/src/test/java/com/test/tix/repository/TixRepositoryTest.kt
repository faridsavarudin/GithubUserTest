package com.test.tix.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.repository.TixRepository
import com.test.tix.core.app.source.RemoteDataSource
import com.test.tix.dummy.DummyTestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TixRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val repository = TixRepository(remote)

    @Test
    fun getDetailUser() = runBlockingTest {
        val dummyDetailUser = DummyTestModel.getDetailUser()
        val id = dummyDetailUser.value?.data ?: ItemUser()
        Mockito.`when`(remote.getDetailUser(id.login))
            .thenReturn(dummyDetailUser)
        val getDummyUser = repository.getDetailUser(id)
        verify(remote).getDetailUser(id.login)
        Assert.assertNotNull(getDummyUser)
        assertEquals(dummyDetailUser, getDummyUser)
    }

    @Test
    fun searchUser() = runBlockingTest {
        val dummySearchUser = DummyTestModel.getUserSearch()
        Mockito.`when`(remote.searchUser("")).thenReturn(dummySearchUser)
        val searchUser = repository.searchUser("")
        verify(remote).searchUser("")
        Assert.assertNotNull(searchUser)
        assertEquals(dummySearchUser, searchUser)
    }

    @Test
    fun listReposUser() = runBlockingTest {
        val dummyRepoUser = DummyTestModel.getListReposUser()
        Mockito.`when`(remote.getRepoUser("")).thenReturn(dummyRepoUser)
        val repoUser = repository.getRepoUser("")
        verify(remote).getRepoUser("")
        Assert.assertNotNull(repoUser)
        assertEquals(dummyRepoUser, repoUser)
    }
}