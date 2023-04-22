@file:Suppress("DEPRECATION")

package com.example.chatmessenger.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatmessenger.MainActivity
import com.example.chatmessenger.R
import com.example.chatmessenger.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException



class SignInActivity : AppCompatActivity() {

    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit private var fbauth: FirebaseAuth
    lateinit private var pds: ProgressDialog


    lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)


        fbauth = FirebaseAuth.getInstance()


        if (fbauth.currentUser!=null){


            startActivity(Intent(this, MainActivity::class.java))


        }

        pds = ProgressDialog(this)

        binding.signInTextToSignUp.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))


        }


        binding.loginButton.setOnClickListener {



            email = binding.loginetemail.text.toString()
            password = binding.loginetpassword.text.toString()




            if (binding.loginetemail.text.isEmpty()){

                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()


            }


            if (binding.loginetpassword.text.isEmpty()){

                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()


            }


            if (binding.loginetemail.text.isNotEmpty() && binding.loginetpassword.text.isNotEmpty()){


                signIn(password, email)



            }







        }



    }

    private fun signIn(password: String, email: String) {
        pds.show()
        pds.setMessage("Signing In")

        fbauth.signInWithEmailAndPassword(email, password).addOnCompleteListener {


            if (it.isSuccessful){

                pds.dismiss()
                startActivity(Intent(this, MainActivity::class.java))


            } else {

                pds.dismiss()
                Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()


            }


        }.addOnFailureListener {exception->


            when (exception){

                is FirebaseAuthInvalidCredentialsException->{

                    Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()


                }

                else-> {

                    // other exceptions
                    Toast.makeText(applicationContext, "Auth Failed", Toast.LENGTH_SHORT).show()


                }



            }





        }


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()

        pds.dismiss()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        pds.dismiss()

    }

}