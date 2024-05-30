package com.example.bangkit_2024_fp_bpaai.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_fp_bpaai.R
import com.example.bangkit_2024_fp_bpaai.data.local.preference.User
import com.example.bangkit_2024_fp_bpaai.data.local.preference.UserPreferences
import com.example.bangkit_2024_fp_bpaai.ui.auth.login.LoginActivity
import com.example.bangkit_2024_fp_bpaai.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        Handler(Looper.getMainLooper()).postDelayed({
            if (userModel.token == "") {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}