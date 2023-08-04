package com.animall.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.animall.Database.MilkSaleDatabase
import com.animall.Models.MilkSale
import com.animall.adaptors.MilkSaleAdapter
import com.animall.databinding.ActivityViewSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.ViewSalesViewModel
import com.animall.viewmodels.ViewSalesViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ViewSalesActivity : AppCompatActivity(),DatePickerListener {
    private lateinit var binding: ActivityViewSalesBinding
    private lateinit var viewModel: ViewSalesViewModel
    private val milkSalesList: MutableList<MilkSale> = mutableListOf()
    private lateinit var adapter:MilkSaleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupListeners()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = MilkSaleAdapter(milkSalesList)
        binding.recyclerView.let {
            it.adapter=adapter
            it.layoutManager= LinearLayoutManager(this)
        }
    }

    private fun setupObservers() {
        viewModel.message.observe(this){message->
            if(!message.isNullOrEmpty()) Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        }
        viewModel.milkSaleList.observe(this){
                milkSales ->
            milkSalesList.clear()
            milkSalesList.addAll(milkSales)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupListeners() {
        binding.startDateTxt.setOnClickListener {
            val customDatePickerDialog = DatePickerDialogBox(this)
            customDatePickerDialog.showDatePicker(it as EditText, true)
        }
        binding.endDateTxt.setOnClickListener {
            val customDatePickerDialog = DatePickerDialogBox(this)
            customDatePickerDialog.showDatePicker(it as EditText, false)
        }
        binding.getRecord.setOnClickListener {
            viewModel.validatedDatesAndGetData()
        }
        binding.clearBtn.setOnClickListener {
            binding.startDateTxt.text=null
            binding.startDateTxt.clearFocus()
            binding.endDateTxt.text=null
            binding.endDateTxt.clearFocus()
            viewModel.clearAllData()
        }
    }

    private fun setupViewModel() {
        val database by lazy { MilkSaleDatabase.getDatabase(this) }
        val repository by lazy { MilkSaleRepository(database.milkSaleDao()) }
        viewModel = ViewModelProvider(this, ViewSalesViewModelFactory(repository)).get(ViewSalesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }


    override fun onDateSelected(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        editText: EditText,
        isStartDate: Boolean
    ) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        editText.setText(dateFormat.format(calendar.time))
        viewModel.onDateChanged(year,month,dayOfMonth,isStartDate)

    }
}
