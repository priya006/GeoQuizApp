package com.example.geoquiz.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.geoquiz.databinding.ActivityCheatBinding


class CheatActivity : AppCompatActivity() {

    private lateinit var cheatActivityBinding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize the binding object
        cheatActivityBinding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(cheatActivityBinding.root)
        onBackPressedDispatcher.addCallback(this, callBack)

        val answer = intent.getBooleanExtra(EXTRA_KEY, false)
        //Show answer button is clicked and answer displays
        cheatActivityBinding.showanswer.setOnClickListener {
            cheatActivityBinding.answerTextView.text = answer.toString()
        }
    }


    private val callBack = object : OnBackPressedCallback(true) {
        /**
         * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
         */
        override fun handleOnBackPressed() {
            finish()
        }
    }

    /**
     * Creates a new [Intent] to start [CheatActivity] with the specified answer.
     *
     * This method creates an intent to start [CheatActivity] with the necessary extras.
     * The answer parameter determines whether the answer is true or false and is passed as an extra.
     *
     * @param packageContext The context from which the intent will be created.
     * @param answer The boolean value representing whether the answer is true or false.
     * @return An [Intent] object to start [CheatActivity] with the specified answer as an extra.
     */
    companion object {
        private const val EXTRA_KEY = "EXTRA_KEY"
        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_KEY, answer)
            }
        }
    }
}