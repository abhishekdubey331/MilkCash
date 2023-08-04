package com.animall.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.animall.Database.MilkSaleDatabase
import com.animall.databinding.ActivityRecordSalesBinding
import com.animall.repositories.MilkSaleRepository
import com.animall.viewmodels.RecordSalesViewModel
import com.animall.viewmodels.RecordSalesViewModelFactory

class RecordSalesActivity : AppCompatActivity() ,DatePickerListener{
    private lateinit var binding: ActivityRecordSalesBinding
    private lateinit var viewModel: RecordSalesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecordSalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.message.observe(this) { message ->
            when (message) {
                "saved" -> {
                    Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> {
                    Toast.makeText(this, "Error: $message", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    private fun setListeners() {
        binding.DateTxt.setOnClickListener {
            hideKeyboard()
            val customDatePickerDialog = DatePickerDialogBox(this)
            customDatePickerDialog.showDatePicker(it as EditText, false)
        }
        binding.saveSaleButton.setOnClickListener {
            viewModel.saveSale()

        }
    }

    private fun setupViewModel() {
        val database by lazy { MilkSaleDatabase.getDatabase(this) }
        val repository by lazy { MilkSaleRepository(database.milkSaleDao()) }
        viewModel = ViewModelProvider(this, RecordSalesViewModelFactory(repository)).get(RecordSalesViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
    fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onDateSelected(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        editText: EditText,
        isStartDate: Boolean
    ) {
        viewModel.onDateChanged(year,month,dayOfMonth)
    }
}