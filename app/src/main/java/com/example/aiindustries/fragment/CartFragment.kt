package com.example.aiindustries.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.aiindustries.R
import com.example.aiindustries.activity.AddressActivity
import com.example.aiindustries.activity.CategoryActivity
import com.example.aiindustries.activity.ProductDetailsActivity
import com.example.aiindustries.adapter.CartAdapter
import com.example.aiindustries.databinding.FragmentCartBinding
import com.example.aiindustries.roomdb.AppDatabase
import com.example.aiindustries.roomdb.ProductModel


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)



        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()


        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()

        dao.getAllProduct().observe(requireActivity()){
            binding.rvcart.adapter = CartAdapter(requireContext(),it)

            list.clear()
            for (data in  it){
                list.add(data.productId)
            }
            totalCost(it)
        }


        return binding.root

    }

    private fun totalCost(data: List<ProductModel>?) {
        var total = 0
        for (item in data!!){
            total += item.productSp!!.toInt()
        }

        binding.textView12.text = "Total item in cart is ${data.size}"
        binding.textView13.text = "Total cost $total"

        binding.checkout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)
            intent.putExtra("totalCost", total)
            intent.putExtra("productIds", list)
            startActivity(intent)
        }

    }
}


//2.44.00