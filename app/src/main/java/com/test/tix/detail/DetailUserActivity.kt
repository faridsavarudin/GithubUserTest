package com.test.tix.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.tix.core.app.enums.Status
import com.test.tix.core.app.model.ItemUser
import com.test.tix.databinding.ActivityDetailUserBinding
import kotlinx.android.synthetic.main.item_user_repo.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailUserActivity : AppCompatActivity() {

    private lateinit var itemRepoAdapter: ItemRepoAdapter
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUser: ItemUser

    companion object {
        const val EXTRA_ID_USER = "extra_user"
        private const val TAG = "DetailActivity"

        fun getInstance(context: Context, data: ItemUser) {
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra(EXTRA_ID_USER, data)
            context.startActivity(intent)
        }

    }

    private var itemUser: ItemUser? = null
    private val viewModel: DetailViewModel by viewModel { parametersOf(itemUser) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        retrieveData()
        initReposRecycler()
        observerDetailUser()
        observeReposUser()
    }

    private fun observeReposUser() {
        viewModel.getRepository().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoadingRepos(false)

                    if (it.data != null) {
                        val result = it.data
                        if (!result.isNullOrEmpty()) {
                            itemRepoAdapter.set(result)
                        }
                    } else {
                        showError()
                    }
                }
                Status.LOADING -> {
                    showLoadingRepos(true)
                }
                Status.ERROR -> {
                    showLoadingRepos(false)
                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
                    showError()
                }
            }
        }
    }

    private fun retrieveData() {
        itemUser = intent.getParcelableExtra<ItemUser>(EXTRA_ID_USER) as ItemUser
    }

    private fun initReposRecycler() {
        binding.rvRepos.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        itemRepoAdapter = ItemRepoAdapter(context = applicationContext)
        binding.rvRepos.adapter = itemRepoAdapter
    }

    private fun observerDetailUser() {
        viewModel.getDetailUser().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)

                    if (it.data != null) {
                        detailUser = it.data
                        updateUI(detailUser)
                    } else {
                        showError()
                    }
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)
                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
                    showError()
                }
            }
        }
    }

    private fun updateUI(detailUser: ItemUser) {
        if (!detailUser.email.isNullOrEmpty()) {
            binding.linEmail.visibility = View.VISIBLE
            binding.tvEmail.text = detailUser.email
        }
        if (!detailUser.company.isNullOrEmpty()) {
            binding.tvRoleTitle.visibility = View.VISIBLE
            binding.tvRoleTitle.text = detailUser.company
        }
        if (!detailUser.location.isNullOrEmpty()) {
            binding.linLocation.visibility = View.VISIBLE
            binding.tvCity.text = detailUser.location
        }
        if (!detailUser.name.isNullOrEmpty()) {
            binding.tvName.text = detailUser.login
        }

        binding.tvFollowersValue.text = detailUser.followers
        binding.tvFollowingValue.text = detailUser.following
        binding.tvUsername.text = "@${detailUser.login}"
        binding.tvName.text = detailUser.name
        Glide.with(this).load(itemUser?.avatarUrl).into(binding.ivItemPhotoDetail)
    }

    private fun showLoading(showIt: Boolean = true) {
        binding.progressbar.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showLoadingRepos(showIt: Boolean = true) {
        binding.progressbarRepos.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showError(showIt: Boolean = true) {
        binding.tvError.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}