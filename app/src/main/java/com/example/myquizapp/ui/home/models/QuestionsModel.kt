package com.example.myquizapp.ui.home.models

import com.google.gson.annotations.SerializedName

data class QuestionsModel(

	@field:SerializedName("response_code")
	val responseCode: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class ResultsItem(

	@field:SerializedName("difficulty")
	val difficulty: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct_answer")
	val correctAnswer: String? = null,

	@field:SerializedName("incorrect_answers")
	val incorrectAnswers: List<String>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	var selectedAnswer: String? = null
) {
	fun getShuffledOptions(): List<String> {
		val allOptions = mutableListOf<String>()
		// Add correct answer
		correctAnswer?.let { allOptions.add(it) }
		// Add incorrect answers
		incorrectAnswers?.let { allOptions.addAll(it) }

		// Shuffle the combined list
		return allOptions.shuffled()
	}
}
