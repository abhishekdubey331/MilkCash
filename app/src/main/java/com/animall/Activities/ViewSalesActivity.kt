package com.animall.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.animall.Database.MilkSaleDatabase
import com.animall.databinding.ActivityViewSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.RecordSalesViewmodel

class ViewSalesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewSalesBinding
    private lateinit var viewModel: RecordSalesViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

//
//        val datePicker = createDatePickerDialog()
//
//        binding.selectStartDateButton.setOnClickListener {
//            showDatePicker(datePicker)
//            viewModel.isStartDateSelected = true
//        }
//
//        binding.selectEndDateButton.setOnClickListener {
//            showDatePicker(datePicker)
//            viewModel.isStartDateSelected = false
//        }
//
//        binding.filterButton.setOnClickListener {
//            viewModel.fetchSalesBetweenDates()
//        }
//
//
//        viewModel.totalRevenue.observe(this) { revenue ->
//            binding.totalRevenueTextView.text = "Total Revenue: $revenue"
//        }
//
//        viewModel.totalSales.observe(this) { salesCount ->
//            binding.totalSalesTextView.text = "Total Sales: $salesCount"
//        }

    }

    private fun setupViewModel() {
        val database by lazy { MilkSaleDatabase.getDatabase(this) }
        val repository by lazy { MilkSaleRepository(database.milkSaleDao()) }

    }
}


//    private fun createDatePickerDialog(): DatePickerDialog {
//        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//            val calendar = Calendar.getInstance().apply {
//                set(year, month, dayOfMonth)
//            }
//            if (viewModel.isStartDateSelected) {
//                viewModel.startDate.value = calendar.time
//            } else {
//                viewModel.endDate.value = calendar.time
//            }
//        }
//
//        val calendar = Calendar.getInstance()
//        return DatePickerDialog(
//            this,
//            datePickerListener,
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//    }
//
//    private fun showDatePicker(datePicker: DatePickerDialog) {
//        datePicker.show()
//    }

