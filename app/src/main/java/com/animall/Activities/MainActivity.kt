package com.animall.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.animall.databinding.ActivityMainBinding
import com.animall.viewmodels.MainViewmodel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewmodel:MainViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recordSalesBtn.setOnClickListener {
        val intent=Intent(this,RecordSalesActivity::class.java)
            startActivity(intent)
        }
        binding.viewSalesBtn.setOnClickListener {
            val intent=Intent(this,ViewSalesActivity::class.java)
            startActivity(intent)
        }
    }
}