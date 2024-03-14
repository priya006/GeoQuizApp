package com.example.geoquiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.dataclass.Question
import com.google.android.material.snackbar.Snackbar
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var rootView: View
    var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(android.R.id.content)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)



        trueButton.setOnClickListener {
            //Registering the callBack to run
            val trueMessage = getString(R.string.true_button)
            snackBar(trueMessage)
        }


        falseButton.setOnClickListener {
            //Registering the callBack to run
            val falseMessage = getString(R.string.false_button)
            snackBar(falseMessage)
        }

        nextButton.setOnClickListener {
            val textView = findViewById<TextView>(R.id.text_view)
            //Get the questions
            val listOfQuestions = listOfQuestions(context = this@MainActivity)
            var firstQuestion = listOfQuestions.get(currentIndex)
            textView.text = firstQuestion.resID
            currentIndex++

        }
    }


    //List Of Questions
    private fun listOfQuestions(context: Context): MutableList<Question> {
        val questionsList = mutableListOf<Question>()
        questionsList.add(Question(context.getString(R.string.question_mideast), true))
        questionsList.add(Question(context.getString(R.string.question_oceans), false))
        questionsList.add(Question(context.getString(R.string.question_asia), false))
        return questionsList
    }


    //Append the list Of Questions to a StringBuilder
    private fun displayQuestions(questionsList: MutableList<Question>, textView: TextView) {
        val stringBuilder = StringBuilder()
        for (question in questionsList) {
            stringBuilder.append(question).append("\n")
        }
        textView.text = stringBuilder.toString()
    }

    //Update the TextView with new Questions when the Button Next Is Pressed

    private fun snackBar(toastMessage: String) {
        Snackbar.make(rootView, toastMessage, Snackbar.LENGTH_SHORT).show()
    }


}

//Use View Binding and get the access of the layout. Remove the individual reference of views
//Why we need to use the %
//Set the textView with the data in the list - using View binding
//checkAnswer(takes boolean) when the user pressed True or false. Checks the Question object and if the user's reply matches what is
//in the Question object then the corresponding Toast is made

