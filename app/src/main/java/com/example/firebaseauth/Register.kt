package com.example.firebaseauth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    lateinit var editTextEmail : TextInputEditText
    lateinit var editTextPassword : TextInputEditText
    lateinit var buttonReg : Button
    lateinit var auth: FirebaseAuth
    lateinit var progressBar : ProgressBar
    lateinit var loginNowTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)
        loginNowTextView = findViewById(R.id.loginNow)

        loginNowTextView.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }

        buttonReg.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = "${editTextEmail.text}"
            val password = "${editTextPassword.text}"
            
            if(email.isEmpty()){
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if(password.isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            Log.d("test123","usernme $email, password $password")
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Created.",
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.${task.exception?.printStackTrace()}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        })
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}