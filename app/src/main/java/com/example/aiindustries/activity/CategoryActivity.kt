package com.example.aiindustries.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView
import com.example.aiindustries.R
import com.example.aiindustries.adapter.CategoryProductAdapter
import com.example.aiindustries.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("cat"))
    }

    private fun getProducts(category: String?) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory", category ).get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val layoutManager = LinearLayoutManager(this) // You can also use GridLayoutManager or StaggeredGridLayoutManager
                recyclerView.layoutManager = layoutManager

                recyclerView.adapter = CategoryProductAdapter(this, list)
            }
    }
}

//Error aaarha h solve krna h