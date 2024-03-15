package com.example.geoquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.dataclass.Question
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")

    var currentIndex = 0
    var questionsList = mutableListOf<Question>()
    private lateinit var listOfQuestions : List<Question>


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listOfQuestions = listOfQuestions(context = this@MainActivity)

        //inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.setOnClickListener {
            displayNewQuestionToTextView()
        }

        binding.trueButton.setOnClickListener {
            //Check the question object
            if (checkAnswer(true)) {
                snackBar("Correct: The answer is True")
            } else {
                snackBar("Sorry: The answer is False")
            }
        }


        binding.falseButton.setOnClickListener {
            //Registering the callBack to run
            if (checkAnswer(false)) {
                snackBar("Correct: The answer is false")
            } else {
                snackBar("Sorry: The answer is True")
            }
        }

        binding.nextButton.setOnClickListener {
            displayNewQuestionToTextView()
        }
    }

    private fun displayNewQuestionToTextView() {
        val textView = findViewById<TextView>(R.id.text_view)
        //Get the questions
        var firstQuestion = listOfQuestions.get(currentIndex)
        textView.text = firstQuestion.questionString
        currentIndex++
    }


    //List Of Questions
    private fun listOfQuestions(context: Context): MutableList<Question> {

        questionsList.add(Question(context.getString(R.string.question_mideast), true))
        questionsList.add(Question(context.getString(R.string.question_oceans), false))
        questionsList.add(Question(context.getString(R.string.question_asia), false))

        return questionsList
    }


    //Append the list Of Questions to a StringBuilder
    private fun displayQuestions(questionsList: MutableList<Question>) {
        val stringBuilder = StringBuilder()
        for (question in questionsList) {
            stringBuilder.append(question).append("\n")
        }
//        textView.text = stringBuilder.toString()
        binding.textView.text = stringBuilder.toString()
    }

    //Update the TextView with new Questions when the Button Next Is Pressed

    private fun snackBar(toastMessage: String) {
        Snackbar.make(binding.root, toastMessage, Snackbar.LENGTH_SHORT).show()
    }


    private fun checkAnswer(answer: Boolean): Boolean {
        //get the current Question object
        val questionObject = questionsList[currentIndex]
        if (questionObject.answer == answer) {
            return true
        }

        return false
    }
}


