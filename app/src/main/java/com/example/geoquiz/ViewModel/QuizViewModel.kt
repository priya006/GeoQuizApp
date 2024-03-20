package com.example.geoquiz.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import com.example.geoquiz.dataclass.Question

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var questionsList = mutableListOf<Question>()
    //For the Next Button
    var nextIndex = 0

    private val _message = MutableLiveData<Double>()
    val message:LiveData<Double> = _message

    //TODO we must not refer context from viewmodel class. we must remove context
    //List Of Questions
     fun listOfQuestions(context: Context): MutableList<Question> {

        questionsList.add(Question(context.getString(R.string.question_mideast), true))
        questionsList.add(Question(context.getString(R.string.question_oceans), false))
        questionsList.add(Question(context.getString(R.string.question_asia), false))

        return questionsList
    }

     fun handleResponse(userInput: Boolean): Boolean {

        //Check the answer in the questionList if the answer is true the disable the true button
        if (questionsList[nextIndex].answer == true) {
            return true
        }

        if (questionsList[nextIndex].answer == false) {
            return false
        }

        return false
    }
    /*
  Need to make a note of the correct answer - get the number
  no of correct answers/total no of answer(3) = answer*100
 */
     fun calculatePercentage(listOfQuestions: List<Question>,userAnswer: Boolean) {
        val listOfBooleans: MutableList<Boolean> = mutableListOf()
        //compare the user answer and the answer in the Question object
        if (listOfQuestions[currentIndex].answer == userAnswer) {
            listOfBooleans.add(userAnswer)
        }
        //do the math and calculate the percentage
        val percentage = (listOfBooleans.size.toDouble() / questionsList.size.toDouble()) * 100
         val messageToBeDisplayed = "${percentage}".toDouble()
            _message.value = messageToBeDisplayed
        println(   _message.value)
    }

}