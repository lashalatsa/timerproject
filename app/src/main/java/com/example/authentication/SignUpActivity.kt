package com.example.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Log.w
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {

        auth = Firebase.auth
        signUpButton.setOnClickListener {
            userRegistration()
        }
    }

    private fun userRegistration(){
        val email:String = signUpEmail.text.toString()
        val password:String = signUpPassword.text.toString()
        val repeatPassword:String = signUpPasswordRepeat.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()){
            if (password==repeatPassword){
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressBar.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                progressBar.visibility = View.GONE
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    d("signUp", "createUserWithEmail:success")
                                    val user = auth.currentUser

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("SuccessMessage", "User $email Succesfully registered")

                                    startActivity(intent)

                                } else {
                                    // If sign in fails, display a message to the user.
                                    d("signUp", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    Toast.makeText(this,"User registered succesfully!",Toast.LENGTH_SHORT).show()
                }else{Toast.makeText(this,"Email format is not Correct!",Toast.LENGTH_SHORT).show()}

            }else{Toast.makeText(this,"Please enter same passwords!",Toast.LENGTH_SHORT).show()}
        } else{
            Toast.makeText(this,"Please fill all fields!",Toast.LENGTH_SHORT).show()
        }
    }
}