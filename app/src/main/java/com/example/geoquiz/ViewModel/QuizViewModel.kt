package com.example.geoquiz.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.dataclass.Question

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {


    private val CURRENT_INDEX_KEY = "current_index"

    //var currentIndex = 0
    var questionsList = mutableListOf<Question>()

    //For the Next Button
    var nextIndex = 0

    //For the Previous Button
    var previousIndex = 0

    private val _message = MutableLiveData<Double>()
    val message: LiveData<Double> = _message

    //saving the data to savedStateHandle. savedStateHandle will be null when the viewmodel is instantiated the first time
    private var currentIndex = savedStateHandle.get<Int>(CURRENT_INDEX_KEY) ?: 0

    fun getCurrentIndex(): Int {
        return currentIndex
    }

    fun setCurrentIndex(index: Int) {
        currentIndex = index
        savedStateHandle.set(CURRENT_INDEX_KEY, index)
    }

    //TODO: violation of best practice we must not refer context from viewmodel class. Once we learn dependency injection we can remove context
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
    fun calculatePercentage(listOfQuestions: List<Question>, userAnswer: Boolean) {
        val listOfBooleans: MutableList<Boolean> = mutableListOf()
        //compare the user answer and the answer in the Question object
        if (listOfQuestions[currentIndex].answer == userAnswer) {
            listOfBooleans.add(userAnswer)
        }
        //do the math and calculate the percentage
        val percentage = (listOfBooleans.size.toDouble() / questionsList.size.toDouble()) * 100
        val messageToBeDisplayed = "${percentage}".toDouble()
        _message.value = messageToBeDisplayed
    }


    fun displayNextQuestion(binding: ActivityMainBinding, listOfQuestions: List<Question>) {
        binding.falseButton.isEnabled = true
        binding.trueButton.isEnabled = true
        if (getCurrentIndex() < listOfQuestions.size) {
            nextIndex = getCurrentIndex()
            //Get the questions
            var firstQuestion = listOfQuestions[nextIndex]
            binding.textView.text = firstQuestion.questionString
            previousIndex = nextIndex - 1
            if (nextIndex <= 2) {
                nextIndex++
            }
        } else {
            //reset the nextIndex
            nextIndex = 0
        }

    }

    fun displayPreviousQuestion(binding: ActivityMainBinding, listOfQuestions: List<Question>) {

        //Get the questions
        var firstQuestion = listOfQuestions.get(previousIndex)
        binding.textView.text = firstQuestion.questionString
    }

     fun checkAnswer(answer: Boolean,listOfQuestions: List<Question>): Boolean {
        //get the current Question object
        if (listOfQuestions[nextIndex].answer == answer) {
            return true
        }
        return false
    }
}