package com.example.bangkit_2024_fp_bpaai.ui.add

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.data.local.preference.User
import com.example.bangkit_2024_fp_bpaai.data.local.preference.UserPreferences
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.databinding.ActivityAddStoryBinding
import com.example.bangkit_2024_fp_bpaai.ui.ViewModelFactory
import com.example.bangkit_2024_fp_bpaai.utils.getImageUri
import com.example.bangkit_2024_fp_bpaai.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null

    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val viewModel: AddStoryViewModel by viewModels {
        factory
    }

    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        back()

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGallery.setOnClickListener { startGallery() }

        upload()
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivStory.setImageURI(it)
        }
    }

    private fun upload() {
        val edDesc = binding.edDesc.text

        binding.btnUpload.setOnClickListener {
            if (currentImageUri == null || edDesc!!.isEmpty()) {
                showToast(R.string.empty_form_upload)
            } else {
                currentImageUri?.let { uri ->
                    val imageFile = uriToFile(uri, this)
                    val desc = binding.edDesc.text.toString()

                    val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                    val requestBody = desc.toRequestBody("text/plain".toMediaType())
                    val multipartBody = MultipartBody.Part.createFormData(
                        "photo",
                        imageFile.name,
                        requestImageFile
                    )

                    viewModel.addStory(userModel.token!!, multipartBody, requestBody).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE

                                    showToast(R.string.add_story_success)

                                    if (result.data.error == false) {
                                        finish()
                                    }
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE

                                    showToastString(result.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun showToast(message: Int) {
        Toast.makeText(this@AddStoryActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToastString(message: String) {
        Toast.makeText(this@AddStoryActivity, message, Toast.LENGTH_SHORT).show()
    }
}