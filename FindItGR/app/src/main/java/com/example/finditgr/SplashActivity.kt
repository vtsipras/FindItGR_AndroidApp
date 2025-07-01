package com.example.finditgr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.finditgr.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Φορτώνουμε το layout με ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Φορτώνουμε το animation scale+fade για το λογότυπο
        val scaleFadeIn = AnimationUtils.loadAnimation(this, R.anim.scale_fade_in)

        // Ξεκινάμε το animation στο logoImageView
        binding.logoImageView.startAnimation(scaleFadeIn)

        // Φορτώνουμε το animation fade-in για το welcome text
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.welcomeText.startAnimation(fadeIn)

        // Μετά από 2.5 δευτερόλεπτα ανοίγουμε την SelectCategoryActivity και κλείνουμε το SplashActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SelectCategoryActivity::class.java))
            finish()
        }, 2500)
    }
}
