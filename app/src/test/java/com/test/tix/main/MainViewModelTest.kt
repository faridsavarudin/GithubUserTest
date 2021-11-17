package com.test.tix.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.tix.core.app.repository.TixRepository
import com.test.tix.dummy.DummyTestModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: TixRepository

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getEmptyUser() = runBlockingTest {
        Mockito.`when`(repository.searchUser("farid")).thenReturn(DummyTestModel.getUserSearch())
        Assert.assertNotNull(viewModel)
        viewModel.setSearch("farid")
        Assert.assertNotNull(viewModel.getSearched())
        Assert.assertEquals(
            viewModel.getSearched().value?.data?.itemUsers?.size,
            DummyTestModel.getEmptyUserSearch().value?.data?.itemUsers?.size
        )
    }
}