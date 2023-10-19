package com.example.aiindustries.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aiindustries.R
import com.example.aiindustries.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    private lateinit var preference : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference =  this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userPincode.text.toString(),
                binding.City.text.toString(),
                binding.state.text.toString(),
                binding.userAddress.text.toString()
            )
        }
    }

    private fun validateData(number: String, name: String, pincode: String, city: String, state: String, address: String) {
        if (number.isEmpty()||state.isEmpty()||name.isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData(pincode, city, state, address)
        }

    }

    private fun storeData(pinCode: String, city: String, state: String, address: String) {
        val map = hashMapOf<String, Any>()
        map["address"] = address
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        Firebase.firestore.collection("users")
            .document(preference.getString("number","")!!)
            .update(map).addOnSuccessListener {

                val intent = Intent(this,CheckoutActivity::class.java)
                intent.putExtra("productIds", intent.getStringExtra("productIds"))
                startActivity(intent)


            }
            .addOnFailureListener {
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {


        Firebase.firestore.collection("users")
            .document(preference.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userAddress.setText(it.getString("address"))
                binding.City.setText(it.getString("city"))
                binding.state.setText(it.getString("state"))
                binding.userPincode.setText(it.getString("pinCode"))
            }
            .addOnFailureListener {

            }
    }


}


//3.04.30