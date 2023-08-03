package com.animall.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.animall.Database.MilkSaleDatabase
import com.animall.databinding.ActivityRecordSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.RecordSalesViewmodel
import com.animall.viewmodels.RecordSalesViewModelFactory

class RecordSalesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordSalesBinding
    private lateinit var viewModel: RecordSalesViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecordSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        viewModel.selectedDate.observe(this) { selectedDate ->

        }

        binding.saveSaleButton.setOnClickListener {
            viewModel.saveSale()

        }
        viewModel.message.observe(this){message->
          Toast.makeText(this,message,Toast.LENGTH_LONG).show()
        }

    }

    private fun setupViewModel() {
        val database by lazy { MilkSaleDatabase.getDatabase(this) }
        val repository by lazy { MilkSaleRepository(database.milkSaleDao()) }
        viewModel = ViewModelProvider(this, RecordSalesViewModelFactory(repository)).get(RecordSalesViewmodel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}