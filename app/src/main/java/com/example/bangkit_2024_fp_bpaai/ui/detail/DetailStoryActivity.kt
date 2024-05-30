package com.example.bangkit_2024_fp_bpaai.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.databinding.ActivityDetailStoryBinding
import com.example.bangkit_2024_fp_bpaai.utils.withDateFormat

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        back()

        setDetailStoryData()
    }

    private fun setDetailStoryData() {
        val ivStory = intent.extras!!.getString(EXTRA_STORY_IMAGE)
        val tvFullName = intent.extras!!.getString(EXTRA_STORY_NAME)
        val tvCreatedAt = intent.extras!!.getString(EXTRA_STORY_CREATED_AT)
        val tvDesc = intent.extras!!.getString(EXTRA_STORY_DESC)

        Glide.with(this@DetailStoryActivity)
            .load(ivStory)
            .into(binding.ivStory)
        binding.tvFullName.text = tvFullName
        binding.tvCreatedAt.text = tvCreatedAt!!.withDateFormat()
        binding.tvDesc.text = tvDesc
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_STORY_IMAGE = "extra_story_image"
        const val EXTRA_STORY_NAME = "extra_story_name"
        const val EXTRA_STORY_CREATED_AT = "extra_story_created_at"
        const val EXTRA_STORY_DESC = "extra_story_desc"
    }
}