package com.animall.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.animall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
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