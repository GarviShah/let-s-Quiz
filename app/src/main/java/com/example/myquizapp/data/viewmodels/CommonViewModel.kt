package com.example.myquizapp.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myquizapp.roomdb.QuizScore
import com.example.myquizapp.roomdb.QuizScoreDao
import com.example.myquizapp.utils.Constants
import com.example.myquizapp.data.repository.CommonRepository
import com.example.myquizapp.network.NetworkHelper
import com.example.myquizapp.network.Resource
import com.example.myquizapp.ui.home.models.QuestionsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
/**
 * CommonViewModel is a class that acts as a ViewModel in the MVVM architecture.
 * It uses the CommonRepository and NetworkHelper to make API calls and handle network status.
 * It provides LiveData objects for observing API responses in the UI layer.
 *
 * @property repository The repository used to make API calls.
 * @property networkHelper The helper used to check network status.
 * @constructor Creates a CommonViewModel instance with the provided CommonRepository and NetworkHelper.
 */
class CommonViewModel @Inject constructor(
    private val repository: CommonRepository,
    private val networkHelper: NetworkHelper,
    private val quizScoreDao: QuizScoreDao
) : ViewModel() {

    private val _getQuestionsList: MutableLiveData<Resource<QuestionsModel>> = MutableLiveData()
    val getQuestionsList: LiveData<Resource<QuestionsModel>> get() = _getQuestionsList

    private val _lastScore = MutableLiveData<QuizScore?>()
    val lastScore: LiveData<QuizScore?> get() = _lastScore

    fun getQuizQuestions() = viewModelScope.launch {
        _getQuestionsList.value = Resource.loading(null)
        if (networkHelper.isNetworkConnected()) {
            try {
                repository.getQuizQuestions().let {
                    if (it.isSuccessful) {
                        _getQuestionsList.postValue(Resource.success(it.body()))
                    } else {
                        _getQuestionsList.postValue(Resource.error( Constants.ERROR_UNKNOWN, null))
                    }
                }
            } catch (e: Exception) {
                _getQuestionsList.postValue(
                    Resource.error(
                        Constants.ERROR_UNKNOWN,
                        null
                    )
                )
            }
        } else _getQuestionsList.postValue(
            Resource.error(
                Constants.ERROR_NO_INTERNET,
                null
            )
        )
    }

    fun storeScoreInDatabase(score: Int, totalQuestions: Int) {
        viewModelScope.launch {
            val quizScore = QuizScore(score = score, total = totalQuestions)
            quizScoreDao.insertScore(quizScore)
        }
    }

    fun getLastScoreFromDatabase() {
        viewModelScope.launch {
            _lastScore.value = quizScoreDao.getLastScore()
        }
    }
}