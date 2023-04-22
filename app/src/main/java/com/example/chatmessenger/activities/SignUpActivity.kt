@file:Suppress("DEPRECATION")

package com.example.chatmessenger.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatmessenger.R
import com.example.chatmessenger.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var pd : ProgressDialog
    lateinit var auth : FirebaseAuth
    lateinit var firestore : FirebaseFirestore
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        pd = ProgressDialog(this)


        binding.signUpTextToSignIn.setOnClickListener {

            startActivity(Intent(this, SignInActivity::class.java))


        }

        binding.signUpBtn.setOnClickListener {


            name = binding.signUpEtName.text.toString()
            email = binding.signUpEmail.text.toString()
            password = binding.signUpPassword.text.toString()


            if (binding.signUpEtName.text.isEmpty()){

                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()


            }


            if (binding.signUpEmail.text.isEmpty()){

                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()


            }


            if (binding.signUpPassword.text.isEmpty()){

                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()


            }


            if (binding.signUpEtName.text.isNotEmpty() && binding.signUpEmail.text.isNotEmpty() && binding.signUpPassword.text.isNotEmpty()){


                createAnAccount(name, password, email)



            }







        }

    }

    private fun createAnAccount(name: String, password: String, email: String) {


        pd.show()
        pd.setMessage("Registering User")

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task->

        if (task.isSuccessful){


            val user = auth.currentUser

            val dataHashMap = hashMapOf("userid" to user!!.uid!!, "username" to name, "useremail" to email, "status" to "default",
            "imageUrl" to "https://www.pngarts.com/files/6/User-Avatar-in-Suit-PNG.png")




            firestore.collection("Users").document(user.uid).set(dataHashMap)

            pd.dismiss()
            startActivity(Intent(this, SignInActivity::class.java))






        }





        }




    }
}