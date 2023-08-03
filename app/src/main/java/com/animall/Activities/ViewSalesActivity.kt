package com.animall.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.animall.Database.MilkSaleDatabase
import com.animall.databinding.ActivityViewSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.RecordSalesViewModelFactory
import com.animall.viewmodels.RecordSalesViewmodel
import com.animall.viewmodels.ViewSalesViewModel
import com.animall.viewmodels.ViewSalesViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ViewSalesActivity : AppCompatActivity(),DatePickerListener {
    private lateinit var binding: ActivityViewSalesBinding
    private lateinit var viewModel: ViewSalesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        binding.startDateTxt.setOnClickListener {
            val customDatePickerDialog = DatePickerDialogBox(this)
            customDatePickerDialog.showDatePicker(it as EditText, false)
        }
        binding.endDateTxt.setOnClickListener {
            val customDatePickerDialog = DatePickerDialogBox(this)
            customDatePickerDialog.showDatePicker(it as EditText, false)
        }

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

    }
}
