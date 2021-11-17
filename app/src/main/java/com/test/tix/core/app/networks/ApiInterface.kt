package com.test.tix.core.app.networks

import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.model.ReposResponseItem
import com.test.tix.core.app.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String, @Query("page") page: Int
    ): UsersResponse

    @GET("users/{username}")
    suspend fun getDetailUsers(
        @Path("username") username: String?
    ): ItemUser

    @GET("users/{username}/repos")
    suspend fun getRepoUsers(@Path("username") username: String?
    ): MutableList<ReposResponseItem>
}

