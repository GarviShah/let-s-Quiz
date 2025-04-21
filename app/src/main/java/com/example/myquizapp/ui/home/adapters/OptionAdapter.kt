package com.example.myquizapp.ui.home.adapters

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myquizapp.R
import com.example.myquizapp.utils.UtilMethods.fromHtml
import com.example.myquizapp.databinding.ItemOptionListBinding
import com.example.myquizapp.extension.vibrateCompat


class OptionAdapter(
    private val options: List<String>,
    private val correctAnswer: String,
    private val onOptionSelected: (String, Boolean) -> Unit
) : RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    private var selectedPosition = -1
    private var isAnswered = false

    inner class OptionViewHolder(private val binding: ItemOptionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(optionText: String, position: Int) {
            binding.tvOption.text = fromHtml(optionText)

            val bgRes = when {
                isAnswered && optionText == correctAnswer -> {
                    R.drawable.quiz_option_correct_bg
                }

                isAnswered && position == selectedPosition -> R.drawable.quiz_option_selected_bg
                else -> R.drawable.quiz_option_unselected_bg
            }

            binding.cardOption.setBackgroundResource(bgRes)

            binding.root.setOnClickListener {
                // Allow selection only if not already answered
                if (!isAnswered) {
                    selectedPosition = position
                    isAnswered = true
                    notifyDataSetChanged()
                    val isCorrect =
                        optionText == correctAnswer
                    onOptionSelected(optionText, isCorrect)


                    if (!isCorrect) {
                        // If the answer is incorrect, trigger the shake animation
                        shakeCard(binding.cardOption)
                    }
                }
            }
        }
    }

    private fun shakeCard(view: View) {
        // ObjectAnimator to make the card shake
        val shake = ObjectAnimator.ofFloat(view, "translationX", 0f, 20f, -20f, 20f, -20f, 0f)
        shake.duration = 1000
        shake.start()

        view.context.vibrateCompat(250)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding =
            ItemOptionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun getItemCount() = options.size

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position], position)
    }
}

