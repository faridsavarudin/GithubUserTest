package com.test.tix.core.app.source

import android.util.Log
import androidx.lifecycle.liveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.networks.ApiClient
import com.test.tix.core.app.utils.Resource
import com.test.tix.core.app.utils.testing.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers

class RemoteDataSource: PagingSource<Int, ItemUser>() {
    private val api = ApiClient.apiClient

    fun getDetailUser(id: String?) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            EspressoIdlingResource.increment()

            try {
                emit(Resource.success(data = api.getDetailUsers(id)))
                EspressoIdlingResource.decrement()

            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }


    fun searchUser(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        EspressoIdlingResource.increment()
        try {
            emit(Resource.success(data = api.searchUser(query, 1)))
            EspressoIdlingResource.decrement()
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            EspressoIdlingResource.decrement()
        }
    }

    fun getRepoUser(username: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        EspressoIdlingResource.increment()
        try {
            emit(Resource.success(data = api.getRepoUsers(username)))
            EspressoIdlingResource.decrement()
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            EspressoIdlingResource.decrement()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemUser>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemUser> {
        return try {
            Log.e("LLOOAADDD","LOOAADDD")
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = api.searchUser("farid", nextPage)

            val players = response.itemUsers
            LoadResult.Page(
                data = players!!,
                prevKey = if (FIRST_PAGE_INDEX == FIRST_PAGE_INDEX) null else FIRST_PAGE_INDEX - 1,
                nextKey = if (response.itemUsers.isEmpty()) null else FIRST_PAGE_INDEX + 1
            )

        }
        catch (e: Exception) {
            Log.e("LLOOAADDD ERRORRR " + e.message," heheheh")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

}