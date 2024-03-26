package com.example.geoquiz.Activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.BlurMaskFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
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

    @RequiresApi(Build.VERSION_CODES.S)
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
            if (quizViewModel.checkAnswer(true, listOfQuestions)) {
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
            if (quizViewModel.checkAnswer(false, listOfQuestions)) {
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
        binding.cheatButton?.let { applyBlurEffect(it) }
        var noOfTimesCheatClicked = 0
        binding.cheatButton?.setOnClickListener {
            noOfTimesCheatClicked ++
            val intent = CheatActivity.newIntent(
                this@GeoQuizActivity,
                listOfQuestions[quizViewModel.nextIndex].answer
            )

            // Start an activity for result using registerForActivityResult
            startActivityForResult.launch(intent)

            //After the button is clicked thrice we disable the button
            if(noOfTimesCheatClicked > 2)
            {
                binding.cheatButton?.let { it.isEnabled = false}
            }
        }
        val apiLevel = "API Level ${Build.VERSION.SDK_INT}"
        binding.apiLevel?.text  = apiLevel
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

    /**
     * Sets up a [registerForActivityResult] to handle activity results in the [GeoQuizActivity].
     * When the result is received from the child activity (launched with [startActivityForResult]),
     * it checks if the resultCode is [Activity.RESULT_OK]. If it is, it extracts the string extra
     * with the key "answer_shown" from the result Intent and displays it in a toast message.
     */
    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                //get the data from Intent
                quizViewModel.isCheater = result.data?.getBooleanExtra("answer_shown",false) ?: false
                Toast.makeText(
                    this@GeoQuizActivity,
                    "Received result from ChildActivity $quizViewModel.isCheater",
                    Toast.LENGTH_LONG
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun applyBlurEffect(cheatButton : View){
        //createBlurEffect is available only from API level 31
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val blurEffect = RenderEffect.createBlurEffect(2f, 2f, Shader.TileMode.MIRROR)
            cheatButton.setRenderEffect(blurEffect)
        } else {
            // Fallback for older devices
        }

    }

}



