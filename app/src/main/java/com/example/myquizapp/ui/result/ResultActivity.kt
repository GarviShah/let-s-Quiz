package com.example.myquizapp.ui.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myquizapp.R
import com.example.myquizapp.utils.Constants.IS_FROM_RESULT
import com.example.myquizapp.utils.Intent.SCORE
import com.example.myquizapp.utils.Intent.TOTAL_QUESTIONS
import com.example.myquizapp.core.BaseActivity
import com.example.myquizapp.databinding.ActivityResultBinding
import com.example.myquizapp.extension.loadGif
import com.example.myquizapp.extension.onDoubleTapToExit
import com.example.myquizapp.ui.home.MainActivity

class ResultActivity : BaseActivity() {

    private lateinit var binding: ActivityResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        onDoubleTapToExit()
    }

    private fun initView() {

        setClickListeners()

        getIntentData()
    }

    private fun getIntentData() {
        // Retrieve data from intent
        intent.extras?.apply {
            val score = getInt(SCORE)
            val totalQuestions = getInt(TOTAL_QUESTIONS)

            commonViewModel.storeScoreInDatabase(score, totalQuestions)

            binding.tvResult.text = getString(R.string.result_text)

            val finalScore = "$score / $totalQuestions"
            binding.tvResultScore.text = finalScore
        }

        // Load the congratulatory GIF
        binding.imgCongrats.loadGif(R.drawable.congrets_final)
    }

    private fun setClickListeners() {
        binding.btnNext.setOnClickListener(this)
    }

    override fun onSafeClick(v: View) {
        when (v.id) {
            binding.btnNext.id -> {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(IS_FROM_RESULT, true)
                })
            }
        }
    }
}