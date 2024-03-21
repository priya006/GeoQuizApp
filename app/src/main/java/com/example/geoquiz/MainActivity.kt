package com.example.geoquiz

import android.annotation.SuppressLint
import android.content.ContentValues.TAG

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
            quizViewModel.displayNextQuestion(binding, listOfQuestions)
        }

        binding.trueButton.setOnClickListener {

            //Check the question object
            if (quizViewModel.checkAnswer(true,listOfQuestions)) {
                snackBar("Correct: The answer is True")
            } else {
                snackBar("Sorry: The answer is False")
            }

            if (quizViewModel.handleResponse(true) == true) {
                it.isEnabled = false
            }
            quizViewModel.calculatePercentage(listOfQuestions, true)
            showToastMessage()
        }


        binding.falseButton.setOnClickListener {

            //Registering the callBack to run
            if (quizViewModel.checkAnswer(false,listOfQuestions)) {
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
            quizViewModel.displayNextQuestion(binding, listOfQuestions)
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.displayPreviousQuestion(binding, listOfQuestions)
        }
        quizViewModel.calculatePercentage(listOfQuestions, false)
    }

    private fun showToastMessage() {
        quizViewModel.message.observe(this, Observer { message ->
            Log.d("MainActivity", "Received message: $message")
            Toast.makeText(this@MainActivity, message.toString(), Toast.LENGTH_SHORT).show()
        })
    }


    //Update the TextView with new Questions when the Button Next Is Pressed
    private fun snackBar(toastMessage: String) {
        Snackbar.make(binding.root, toastMessage, Snackbar.LENGTH_SHORT).show()
    }
}



