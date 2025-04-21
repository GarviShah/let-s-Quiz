package com.example.myquizapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquizapp.R
import com.example.myquizapp.utils.UtilMethods.fromHtml
import com.example.myquizapp.databinding.ItemQueListBinding
import com.example.myquizapp.extension.gone
import com.example.myquizapp.extension.toast
import com.example.myquizapp.extension.visible
import com.example.myquizapp.ui.home.models.ResultsItem

class QuestionPagerAdapter(
    private val context: Context,
    private var questions: ArrayList<ResultsItem>,
    private val onAnswerSelected: (Int, String) -> Unit,
    private val onNextClicked: (Int) -> Unit
) : RecyclerView.Adapter<QuestionPagerAdapter.VHolder>() {

    // Stores which question index was answered correctly
    private val answerCorrectnessMap = mutableMapOf<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val binding = ItemQueListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VHolder(binding)
    }


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        // Hide Lottie animation by default
        holder.viewBinding.llAnimation.gone()

        val model = questions[position]

        // Set the question text (HTML-formatted)
        holder.viewBinding.tvQuestion.text = model.question?.let { fromHtml(it) }

        // Shuffle the options
        val shuffledOptions = model.getShuffledOptions()

        holder.viewBinding.tvCount.text =
            buildString {
                append(position + 1)
                append("/")
                append(questions.size)
                append(" ")
                append(context.getString(R.string.questions))
            }

        val optionAdapter = OptionAdapter(
            options = shuffledOptions,
            correctAnswer = model.correctAnswer!!
        ) { selectedOption, isAnsCorrect ->
            // Handle answer selection
            onAnswerSelected(position, selectedOption)
            answerCorrectnessMap[position] = isAnsCorrect

            if (isAnsCorrect) holder.viewBinding.llAnimation.visible()

        }

        holder.viewBinding.rvAnswers.adapter = optionAdapter
        // Change button label based on position
        holder.viewBinding.btnNext.text =
            if (position == questions.lastIndex) context.getString(R.string.submit) else context.getString(
                R.string.next
            )
        holder.viewBinding.btnNext.setOnClickListener {
            position.takeIf { answerCorrectnessMap.containsKey(it) } // Proceed only if answered
                ?.let { onNextClicked(it) } // Call next if valid
                ?: context.toast(context.getString(R.string.select_one_option)) // Otherwise show toast

        }
    }

    override fun getItemCount(): Int = questions.size

    // Called externally to set or refresh the question list
    fun setQueList(questionsList: ArrayList<ResultsItem>) {
        this.questions = questionsList
        notifyDataSetChanged()
    }

    // ViewHolder class holding the binding reference
    inner class VHolder(var viewBinding: ItemQueListBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}
