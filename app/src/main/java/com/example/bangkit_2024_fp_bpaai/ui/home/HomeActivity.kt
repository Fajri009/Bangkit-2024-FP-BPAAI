package com.example.bangkit_2024_fp_bpaai.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.adapter.StoryAdapter
import com.example.bangkit_2024_fp_bpaai.data.local.preference.User
import com.example.bangkit_2024_fp_bpaai.data.local.preference.UserPreferences
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.data.remote.response.ListStoryItem
import com.example.bangkit_2024_fp_bpaai.databinding.ActivityHomeBinding
import com.example.bangkit_2024_fp_bpaai.ui.ViewModelFactory
import com.example.bangkit_2024_fp_bpaai.ui.add.AddStoryActivity
import com.example.bangkit_2024_fp_bpaai.ui.auth.login.LoginActivity
import com.example.bangkit_2024_fp_bpaai.ui.detail.DetailStoryActivity
import com.example.bangkit_2024_fp_bpaai.ui.maps.MapsActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val viewModel: HomeViewModel by viewModels {
        factory
    }

    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        topBar()

        addStory()
    }

    override fun onResume() {
        super.onResume()

        getStoryData(userModel)
    }

    private fun setStory(listStory: List<ListStoryItem>){
        val adapter = StoryAdapter()

        binding.rvStory.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun addStory() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getStoryData(userModel: User) {
        val layoutInflater = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutInflater

        val adapter = StoryAdapter()
        viewModel.getStory(userModel.token!!).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val storiesData = result.data.listStory
                        adapter.submitList(storiesData)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        showToast(result.error)
                    }
                }
            }
        }
        binding.rvStory.adapter = adapter

        adapter.setOnItemClickCallBack(object: StoryAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedStory(data)
            }
        })
    }

    private fun topBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.translate -> {
                    val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                    startActivity(intent)
                    true
                }
                R.id.logout -> {
                    logout()
                    true
                }
                R.id.maps -> {
                    val intent = Intent(this@HomeActivity, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun logout() {
        userModel.token = ""
        userPreferences.setUser(userModel)

        val intent = Intent(this@HomeActivity, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun showSelectedStory(data: ListStoryItem) {
        val moveWithParcelableIntent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailStoryActivity.EXTRA_STORY_NAME, data.name)
        moveWithParcelableIntent.putExtra(DetailStoryActivity.EXTRA_STORY_IMAGE, data.photoUrl)
        moveWithParcelableIntent.putExtra(DetailStoryActivity.EXTRA_STORY_CREATED_AT, data.createdAt)
        moveWithParcelableIntent.putExtra(DetailStoryActivity.EXTRA_STORY_DESC, data.description)
        startActivity(moveWithParcelableIntent)
    }

    private fun showToast(message: String?) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
    }
}