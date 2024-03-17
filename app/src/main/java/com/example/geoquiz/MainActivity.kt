package com.example.geoquiz

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.dataclass.Question
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")

    //For the Next Button
    var nextIndex = 0

    //For the Previous Button
    var previousIndex = 0
    var questionsList = mutableListOf<Question>()
    private lateinit var listOfQuestions: List<Question>


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Create() called")
        listOfQuestions = listOfQuestions(context = this@MainActivity)

        //inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.setOnClickListener {
            displayNextQuestion()
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
            displayNextQuestion()
        }

        binding.previousButton.setOnClickListener {
            displayPreviousQuestion()
        }
    }

    private fun displayNextQuestion() {

        val textView = findViewById<TextView>(R.id.text_view)
        if (nextIndex < listOfQuestions.size) {
            //Get the questions
            var firstQuestion = listOfQuestions[nextIndex]
            textView.text = firstQuestion.questionString
            previousIndex = nextIndex - 1
            if (nextIndex <= 2) {
                nextIndex++
            }
        } else {
            //reset the nextIndex
            nextIndex = 0
        }
    }


    private fun displayPreviousQuestion() {
        val textView = findViewById<TextView>(R.id.text_view)
        //Get the questions
        var firstQuestion = listOfQuestions.get(previousIndex)
        textView.text = firstQuestion.questionString
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
        val questionObject = questionsList[nextIndex]
        if (questionObject.answer == answer) {
            return true
        }

        return false
    }


}


