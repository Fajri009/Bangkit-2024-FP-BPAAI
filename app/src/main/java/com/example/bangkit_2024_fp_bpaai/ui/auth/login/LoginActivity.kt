package com.example.bangkit_2024_fp_bpaai.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.data.local.preference.User
import com.example.bangkit_2024_fp_bpaai.data.local.preference.UserPreferences
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.databinding.ActivityLoginBinding
import com.example.bangkit_2024_fp_bpaai.ui.ViewModelFactory
import com.example.bangkit_2024_fp_bpaai.ui.auth.register.RegisterActivity
import com.example.bangkit_2024_fp_bpaai.ui.home.HomeActivity
import com.example.bangkit_2024_fp_bpaai.utils.isValidEmail

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userModel: User = User()
    private lateinit var userPreference: UserPreferences

    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreferences(this)

        playAnimation()
        signUp()
        login()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun playAnimation() {
        val ivLogo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(1000)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(800)
        val tvEnter = ObjectAnimator.ofFloat(binding.tvEnter, View.ALPHA, 1f).setDuration(800)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(800)
        val edLoginEmail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(800)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(800)
        val edLoginPassword =
            ObjectAnimator.ofFloat(binding.edLayoutLoginPassword, View.ALPHA, 1f).setDuration(800)
        val linearLayout =
            ObjectAnimator.ofFloat(binding.linearLayout, View.ALPHA, 1f).setDuration(800)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(800)

        AnimatorSet().apply {
            playSequentially(
                ivLogo,
                tvLogin,
                tvEnter,
                tvEmail,
                edLoginEmail,
                tvPassword,
                edLoginPassword,
                linearLayout,
                btnLogin
            )
            startDelay = 100
            start()
        }
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val edLoginEmail = binding.edLoginEmail.text
            val edLoginPassword = binding.edLoginPassword.text

            if (edLoginEmail!!.isEmpty() || edLoginPassword!!.isEmpty()) {
                showToast(R.string.empty_form)
            } else if (!isValidEmail(edLoginEmail.toString()) || edLoginPassword.length < 8) {
                showToast(R.string.invalid_form)
            } else {
                viewModel.login(
                    edLoginEmail.toString(),
                    edLoginPassword.toString()
                ).observe(this) { result ->
                    if (result != null) {
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                binding.progressBar.visibility = View.GONE
                                val response = result.data
                                if (response.error == true) {
                                    showToastString(response.message)
                                } else {
                                    val loginResult = response.loginResult
                                    userModel.token = loginResult?.token
                                    userPreference.setUser(userModel)
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
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

    private fun showToast(message: Int) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToastString(message: String?) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}