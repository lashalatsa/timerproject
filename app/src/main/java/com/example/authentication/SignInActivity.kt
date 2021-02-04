package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_authentication.*
import kotlinx.android.synthetic.main.activity_authentication.loginButton
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        init()
    }
    private fun init(){
        auth = Firebase.auth
        loginButton.setOnClickListener {
            signIn()
        }
    }
    private fun signIn(){
        val email:String = loginEmail.text.toString()
        val password = loginPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            progressBarLogin.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBarLogin.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            d("signIn", "signInWithEmail:success")
                            val user = auth.currentUser

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("SuccessMessage", "Welcome back $email")
                            startActivity(intent)

                            Toast.makeText(this, "User succesfully authorized",Toast.LENGTH_SHORT).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            d("signIn", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "User Authentication Failed:"+task.exception?.message,Toast.LENGTH_LONG).show()
                        }

                    }


        }else{Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()}
    }
}
