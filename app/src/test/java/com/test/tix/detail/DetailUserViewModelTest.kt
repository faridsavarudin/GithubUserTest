package com.test.tix.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.repository.TixRepository
import com.test.tix.dummy.DummyTestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailUserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DetailViewModel
    @Mock
    private lateinit var repository: TixRepository

    @Before
    fun setUp() {
        viewModel = DetailViewModel(
            repository,
            ItemUser(login = "faridsavarudin")
        )
    }

    @Test
    fun getDetailUserCoroutine() = runBlockingTest {

        Mockito.`when`(repository.getDetailUser(itemUser = ItemUser(login = "faridsavarudin")))
            .thenReturn(DummyTestModel.getDetailUser())
        Assert.assertNotNull(viewModel)
        viewModel.initDetailUser()
        Assert.assertNotNull(viewModel.getDetailUser())
        assertEquals(
            viewModel.getDetailUser().value?.data,
            DummyTestModel.getDetailUser().value?.data
        )
    }


    @Test
    fun getErrorUserCoroutine() = runBlockingTest {
        Mockito.`when`(repository.getDetailUser(itemUser = ItemUser(login = "faridsavarudin")))
            .thenReturn(DummyTestModel.getDetailUserError())
        viewModel.initDetailUser()
        assertEquals(
            DummyTestModel.getDetailUserError().value?.message,
            viewModel.getDetailUser().value?.message,
        )
    }

    @Test
    fun getRepoUserCoroutine() = runBlockingTest {
        Mockito.`when`(repository.getRepoUser("faridsavarudin")).thenReturn(DummyTestModel.getListReposUser())
        Assert.assertNotNull(viewModel)
        viewModel.initRepos()
        Assert.assertNotNull(viewModel.getRepository())
        assertEquals(
            viewModel.getRepository().value?.data?.get(0)?.owner?.login,
            DummyTestModel.getListReposUser().value?.data?.get(0)?.owner?.login
        )
    }
}