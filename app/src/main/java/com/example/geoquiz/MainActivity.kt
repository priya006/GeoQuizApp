package com.example.geoquiz

import android.annotation.SuppressLint
import android.content.ContentValues.TAG

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.geoquiz.ViewModel.QuizViewModel
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.dataclass.Question
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")




    private lateinit var listOfQuestions: List<Question>
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "Create() called")
        listOfQuestions = quizViewModel.listOfQuestions(context = this@MainActivity)
        Log.d(TAG, "Got a QuizViewmodel ${quizViewModel}")

        //inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.setOnClickListener {
            quizViewModel.displayNextQuestion(binding,listOfQuestions)
        }

        binding.trueButton.setOnClickListener {

            //Check the question object
            if (checkAnswer(true)) {
                snackBar("Correct: The answer is True")
            } else {
                snackBar("Sorry: The answer is False")
            }

            if (quizViewModel.handleResponse(true) == true) {
                it.isEnabled = false
            }
            quizViewModel.calculatePercentage(listOfQuestions, true)
            println(quizViewModel.message)
            showToastMessage()
        }


        binding.falseButton.setOnClickListener {

            //Registering the callBack to run
            if (checkAnswer(false)) {
                snackBar("Correct: The answer is false")
            } else {
                snackBar("Sorry: The answer is True")
            }

            if (quizViewModel.handleResponse(false) == false) {
                it.isEnabled = false
            }

            showToastMessage()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.displayNextQuestion(binding,listOfQuestions)
        }

        binding.previousButton.setOnClickListener {
            displayPreviousQuestion()
        }
        quizViewModel.calculatePercentage(listOfQuestions, false)
    }

    private fun showToastMessage() {
        quizViewModel.message.observe(this, Observer { message ->
            Log.d("MainActivity", "Received message: $message")
            Toast.makeText(this@MainActivity, message.toString(), Toast.LENGTH_SHORT).show()
        })
    }



    private fun displayPreviousQuestion() {
        val textView = findViewById<TextView>(R.id.text_view)
        //Get the questions
        var firstQuestion = listOfQuestions.get(quizViewModel.previousIndex)
        textView.text = firstQuestion.questionString
    }


    //Append the list Of Questions to a StringBuilder
    private fun displayQuestions(questionsList: MutableList<Question>) {
        val stringBuilder = StringBuilder()
        for (question in questionsList) {
            stringBuilder.append(question).append("\n")
        }
        binding.textView.text = stringBuilder.toString()
    }

    //Update the TextView with new Questions when the Button Next Is Pressed

    private fun snackBar(toastMessage: String) {
        Snackbar.make(binding.root, toastMessage, Snackbar.LENGTH_SHORT).show()
    }


    private fun checkAnswer(answer: Boolean): Boolean {
        //get the current Question object
        if (listOfQuestions[quizViewModel.nextIndex].answer == answer) {
            return true
        }

        return false
    }
}



