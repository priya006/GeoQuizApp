package com.example.geoquiz.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var cheatActivityBinding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cheatActivityBinding.root)
        onBackPressedDispatcher.addCallback(this, callBack)

    }


    private val callBack = object : OnBackPressedCallback(true) {
        /**
         * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
         */
        override fun handleOnBackPressed() {
            finish()
        }

    }


}