package com.test.tix.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.tix.core.app.enums.Status
import com.test.tix.databinding.ActivityMainBinding
import com.test.tix.detail.DetailUserActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainAdapter
    private val viewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerview()
        initSearch()
        observerSearch()
    }

    private fun initRecyclerview() {
        binding.rvUser.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = MainAdapter {
            DetailUserActivity.getInstance(this, it)
        }
        binding.rvUser.adapter = adapter
    }


    private fun observerSearch() {
        viewModel.getSearched().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)

                    if (it.data != null) {
                        val result = it.data.itemUsers
                        if (!result.isNullOrEmpty()) {
                            showNotFound(false)
                            adapter.set(result)
                        } else {
                            showNotFound()
                        }
                    } else {
                        showError()
                    }
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)
                    showError()
                    Log.e(
                        MainActivity::class.java.name,
                        "${it.status}, ${it.message} and ${it.data}"
                    )
                }
            }
        }
    }

    private fun showNotFound(showIt: Boolean = true) {
        if (showIt) {
            binding.incNotFound.root.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.incNotFound.root.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE

        }
    }

    private fun showLoading(showIt: Boolean = true) {
        binding.incLoading.root.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showError(showIt: Boolean = true) {
        binding.incError.root.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun initSearch() {
        binding.sbUsers.apply {
            setOnClickListener {
                onActionViewExpanded()
            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText.isNotEmpty()) {
                        viewModel.setSearch(newText)
                    } else {
                        adapter.clearAll()
                    }
                    return true
                }
            })
        }
    }
}