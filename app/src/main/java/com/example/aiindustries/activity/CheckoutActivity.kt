package com.example.aiindustries.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aiindustries.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity() , PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        checkout.setKeyID("<rzp_test_deTONJhwSZzH53>");

        try {
            val options = JSONObject()
            options.put("name", "Ai Industries")
            options.put("description", "The Name You Can Trust")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#673AB7")
            options.put("currency", "INR")
            options.put("amount", "50000") //pass amount in currency subunits
            options.put("prefill.email", "officialaiindustries@gmail.com")
            options.put("prefill.contact", "9079136297")

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Error", Toast.LENGTH_SHORT).show()
    }
}

