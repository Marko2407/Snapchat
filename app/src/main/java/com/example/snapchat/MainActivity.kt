package com.example.snapchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    var emailEditText: EditText?= null
    var passwordEditText: EditText?= null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if (mAuth.currentUser != null){
            logIn()
        }
    }

    fun goClicked(view: View){
        //Check if we can log in the user
        mAuth.signInWithEmailAndPassword( emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                logIn()
                } else {
                    //Sign up the user
                mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            //Add to database
                            logIn()
                        }else{
                            Toast.makeText(this,"Login Failed. Try Again.", Toast.LENGTH_SHORT).show()

                        }

                    }

                }
            }

    }

    fun logIn(){
        //move to next Activity
        val intent = Intent(this,SnapsActivity::class.java)
        startActivity(intent)
    }
}