package com.example.myquizapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.myquizapp.R
import com.example.myquizapp.utils.ApiResponse.STATUS_CODE_200
import com.example.myquizapp.utils.Constants.IS_FROM_RESULT
import com.example.myquizapp.utils.Intent.SCORE
import com.example.myquizapp.utils.Intent.TOTAL_QUESTIONS
import com.example.myquizapp.utils.UtilMethods
import com.example.myquizapp.core.BaseActivity
import com.example.myquizapp.databinding.ActivityMainBinding
import com.example.myquizapp.extension.gone
import com.example.myquizapp.extension.onDoubleTapToExit
import com.example.myquizapp.network.Status
import com.example.myquizapp.ui.home.adapters.QuestionPagerAdapter
import com.example.myquizapp.ui.home.models.ResultsItem
import com.example.myquizapp.ui.result.ResultActivity

class MainActivity : BaseActivity() {
    private var mQueListPagerAdapter: QuestionPagerAdapter? = null
    private var binding: ActivityMainBinding? = null
    private var questionsList = ArrayList<ResultsItem>()
    private val userAnswers = mutableListOf<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
        onDoubleTapToExit()
    }

    override fun onSafeClick(v: View) {
        when (v.id) {
            binding?.btnStartQuiz?.id -> {
                commonViewModel.getQuizQuestions()
            }
        }
    }

    private fun initView() {
        getIntentData()
        setObserver()
        setOnClickListener()
        setUpAdapter()
    }

    private fun getIntentData() {
        binding?.apply {
            intent?.extras?.apply {
                val isFromResult = getBoolean(IS_FROM_RESULT, false)
                if (isFromResult) {
                    // Trigger quiz loading if coming from result screen
                    btnStartQuiz.post {
                        btnStartQuiz.performClick()
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding?.viewPager?.apply {
            isUserInputEnabled = false // Disable swipe, use button only

            mQueListPagerAdapter = QuestionPagerAdapter(
                this@MainActivity,
                questionsList,
                onAnswerSelected = { questionIndex, selectedOption ->
                    // Store the selected answer in the QuestionsModel and userAnswers list
                    questionsList[questionIndex].selectedAnswer = selectedOption

                    if (userAnswers.size <= questionIndex)
                        userAnswers.add(selectedOption)
                    else
                        userAnswers[questionIndex] = selectedOption

                },
                onNextClicked = { currentPosition ->
                    // If it's the last question, calculate score, else move to next
                    if (currentPosition == questionsList.lastIndex)
                        calculateScore()
                    else
                        currentItem = currentPosition + 1

                }
            )

            adapter = mQueListPagerAdapter!!
        }
    }

    private fun calculateScore() {
        var score = 0
        for (i in questionsList.indices) {
            // Check if the user's answer matches the correct answer
            if (userAnswers.getOrNull(i) == questionsList[i].correctAnswer) score++

        }
        // Show the score in a Toast message
        startActivity(Intent(this, ResultActivity::class.java).apply {
            putExtra(SCORE, score)
            putExtra(TOTAL_QUESTIONS, questionsList.size)
        })
    }


    private fun setObserver() {

        commonViewModel.getLastScoreFromDatabase()

        commonViewModel.lastScore.observe(this) { lastScore ->
            // Format score, fallback to empty string if null
            val lastFinalScore =
                lastScore?.let { "${getString(R.string.your_last_score)} ${it.score}/${it.total}" }
                    ?: ""

            binding?.tvLastScore?.text = lastFinalScore
        }

        commonViewModel.getQuestionsList.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress(this)
                }

                Status.SUCCESS -> {
                    it.data?.let { queResponse ->
                        if (queResponse.responseCode != null && queResponse.responseCode == STATUS_CODE_200) {
                            val data = queResponse.results
                            if (!data.isNullOrEmpty()) {
                                questionsList.addAll(data as ArrayList<ResultsItem>) // Add questions to local list

                                if (questionsList.size > 0) {
                                    binding?.flMain?.gone()
                                    mQueListPagerAdapter?.setQueList(questionsList)
                                }
                            } else {
                                UtilMethods.showSnackBar(
                                    this,
                                    binding?.getRoot(),
                                    it.message.toString()
                                )
                            }
                        } else {
                            UtilMethods.showSnackBar(
                                this,
                                binding?.getRoot(),
                                it.message.toString()
                            )
                        }
                    }
                    hideProgress()
                }

                Status.ERROR -> {
                    hideProgress()
                    UtilMethods.showSnackBar(
                        this,
                        binding?.getRoot(),
                        it.message.toString()
                    )
                }
            }
        }
    }

    private fun setOnClickListener() {
        // Set click listener for the Start Quiz button
        binding?.btnStartQuiz?.setOnClickListener(this)
    }
}