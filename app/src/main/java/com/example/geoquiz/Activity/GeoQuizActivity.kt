package com.example.geoquiz.Activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.geoquiz.ViewModel.QuizViewModel
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.dataclass.Question
import com.google.android.material.snackbar.Snackbar


class GeoQuizActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")


    private lateinit var listOfQuestions: List<Question>
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "Create() called")
        listOfQuestions = quizViewModel.listOfQuestions(context = this@GeoQuizActivity)
        Log.d(TAG, "Got a QuizViewmodel ${quizViewModel}")

        //inflate the layout using view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.setOnClickListener {
            quizViewModel.displayNextQuestion(binding, listOfQuestions)
        }

        binding.trueButton.setOnClickListener {

            //Check the question object
            if (quizViewModel.checkAnswer(true,listOfQuestions)) {
                snackBar("Correct: The answer is True")
            } else {
                snackBar("Sorry: The answer is False")
            }

            if (quizViewModel.disableTheButton(true) == true) {
                it.isEnabled = false
            }
            quizViewModel.calculatePercentageOftheCorrectAnswers(listOfQuestions, true)
            showToastMessage()
        }


        binding.falseButton.setOnClickListener {

            //Registering the callBack to run
            if (quizViewModel.checkAnswer(false,listOfQuestions)) {
                snackBar("Correct: The answer is false")
            } else {
                snackBar("Sorry: The answer is True")
            }

            if (quizViewModel.disableTheButton(false) == false) {
                it.isEnabled = false
            }

            showToastMessage()
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.displayNextQuestion(binding, listOfQuestions)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.displayPreviousQuestion(binding, listOfQuestions)
        }
        quizViewModel.calculatePercentageOftheCorrectAnswers(listOfQuestions, false)

        binding.cheatButton?.setOnClickListener {
            val intent = Intent(this,CheatActivity::class.java)
            //From GeoQuizActivity we are intending to start CheatActivity
            startActivity(intent)
        }
    }

    private fun showToastMessage() {
        quizViewModel.message.observe(this, Observer { message ->
            Log.d("MainActivity", "Received message: $message")
            Toast.makeText(this@GeoQuizActivity, message.toString(), Toast.LENGTH_SHORT).show()
        })
    }


    //Update the TextView with new Questions when the Button Next Is Pressed
    private fun snackBar(toastMessage: String) {
        Snackbar.make(binding.root, toastMessage, Snackbar.LENGTH_SHORT).show()
    }
}



