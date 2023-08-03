package com.animall.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.animall.Database.MilkSaleDatabase
import com.animall.R
import com.animall.databinding.ActivityRecordSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.MainViewmodel
import com.animall.viewmodels.WordViewModelFactory

class RecordSalesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordSalesBinding
    private lateinit var viewModel:MainViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecordSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database by lazy { MilkSaleDatabase.getDatabase(this) }
        val repository by lazy { MilkSaleRepository(database.milkSaleDao()) }
        viewModel = ViewModelProvider(this, WordViewModelFactory(repository)).get(MainViewmodel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.selectedDate.observe(this) { selectedDate ->

        }

        binding.saveSaleButton.setOnClickListener {
            viewModel.saveSale()

        }
        viewModel.message.observe(this){message->
          Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }

    }
}