package com.example.geoquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(android.R.id.content)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

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
    }

    private fun snackBar(toastMessage: String) {
        Snackbar.make(rootView, toastMessage, Snackbar.LENGTH_SHORT).show()
    }

    //list of Question objects along with index for the list
    //Data class for question


}



