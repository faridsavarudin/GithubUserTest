package com.test.tix.dummy

import androidx.lifecycle.MutableLiveData
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.model.Owner
import com.test.tix.core.app.model.ReposResponseItem
import com.test.tix.core.app.model.UsersResponse
import com.test.tix.core.app.utils.Resource

object DummyTestModel {

    fun getUserSearch(): MutableLiveData<Resource<UsersResponse>> {
        val user = MutableLiveData<Resource<UsersResponse>>()
        user.value = Resource.success(
            UsersResponse(
                itemUsers = listOf(
                    ItemUser(
                        id = 1,
                        gistsUrl = "",
                        name = "faridsavarudin",
                        avatarUrl = "",
                        email = "",
                        location = "Bandung",

                    )
                )
            )
        )
        return user
    }

    fun getEmptyUserSearch(): MutableLiveData<Resource<UsersResponse>> {
        val user = MutableLiveData<Resource<UsersResponse>>()
        user.value = Resource.success(
            UsersResponse(
                itemUsers = null
            )
        )
        return user
    }

    fun getListReposUser(): MutableLiveData<Resource<MutableList<ReposResponseItem>>> {
        val user = MutableLiveData<Resource<MutableList<ReposResponseItem>>>()
        user.value = Resource.success(
            data = mutableListOf(
                ReposResponseItem(
                    allowForking = true,
                    stargazersCount = 0,
                    description = "",
                    id = 0,
                    owner = Owner(login = "faridsavarudin")
                )
            )
        )
        return user
    }

    fun getDetailUser(): MutableLiveData<Resource<ItemUser>> {
        val user = MutableLiveData<Resource<ItemUser>>()
        user.value = Resource.success(
            ItemUser(
                id = 1,
                location = "Bandung",
                name = "faridsavarudin",
                company = "",
                login = "faridsavarudin"

            )
        )

        return user
    }

    fun getDetailUserError(): MutableLiveData<Resource<ItemUser>> {
        val liveData = MutableLiveData<Resource<ItemUser>>()
        liveData.value = Resource.error(null, "Error Load Data: No Internet")
        return liveData
    }


}