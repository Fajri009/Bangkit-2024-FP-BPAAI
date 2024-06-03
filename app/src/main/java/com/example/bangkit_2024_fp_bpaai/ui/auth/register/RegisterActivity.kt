package com.example.bangkit_2024_fp_bpaai.ui.auth.register

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.data.remote.Result
import com.example.bangkit_2024_fp_bpaai.databinding.ActivityRegisterBinding
import com.example.bangkit_2024_fp_bpaai.ui.ViewModelFactory
import com.example.bangkit_2024_fp_bpaai.ui.auth.login.LoginActivity
import com.example.bangkit_2024_fp_bpaai.utils.isValidEmail

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.apply {
            tvLogin.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            btnSignUp.setOnClickListener {
                signUp()
            }
        }
    }

    private fun playAnimation() {
        val ivLogo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(1000)
        val tvSignUp = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(800)
        val tvEnter = ObjectAnimator.ofFloat(binding.tvEnter, View.ALPHA, 1f).setDuration(800)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(800)
        val edRegisterName =
            ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(800)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(800)
        val edRegisterEmail =
            ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(800)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(800)
        val edRegisterPassword =
            ObjectAnimator.ofFloat(binding.edLayoutRegisterPassword, View.ALPHA, 1f)
                .setDuration(800)
        val linearLayout =
            ObjectAnimator.ofFloat(binding.linearLayout, View.ALPHA, 1f).setDuration(800)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(800)

        AnimatorSet().apply {
            playSequentially(
                ivLogo,
                tvSignUp,
                tvEnter,
                tvName,
                edRegisterName,
                tvEmail,
                edRegisterEmail,
                tvPassword,
                edRegisterPassword,
                linearLayout,
                btnSignUp
            )
            startDelay = 100
            start()
        }
    }

    private fun signUp() {
        val edRegisterName = binding.edRegisterName.text
        val edRegisterEmail = binding.edRegisterEmail.text
        val edRegisterPassword = binding.edRegisterPassword.text

        if (edRegisterName!!.isEmpty() || edRegisterEmail!!.isEmpty() || edRegisterPassword!!.isEmpty()) {
            showToast(R.string.empty_form)
        } else if (!isValidEmail(edRegisterEmail.toString()) || edRegisterPassword.length < 8) {
            showToast(R.string.invalid_form)
        } else {
            viewModel.register(
                edRegisterName.toString(),
                edRegisterEmail.toString(),
                edRegisterPassword.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE

                            showToast(R.string.try_login)

                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
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

    private fun showToast(message: Int) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showToastString(message: String?) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}