package com.animall.viewmodels

import androidx.lifecycle.*
import com.animall.Entity.MilkSaleEntity
import com.animall.Models.MilkSale
import com.animall.repositories.MilkSaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RecordSalesViewModel(private val repository: MilkSaleRepository): ViewModel() {
    val quantity = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    private val selectedDate = MutableLiveData<Date>()

    private val _quantityError = MutableLiveData<String?>()
    val quantityError: MutableLiveData<String?>
        get() = _quantityError

    private val _priceError = MutableLiveData<String?>()
    val priceError: MutableLiveData<String?>
        get() = _priceError

    private val _dateError = MutableLiveData<String?>()
    val dateError: MutableLiveData<String?>
        get() = _dateError

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _selectedDateFormatted = MutableLiveData<String>()
    val selectedDateFormatted: LiveData<String>
    get() = _selectedDateFormatted





    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, monthOfYear, dayOfMonth)
        }
        selectedDate.value = calendar.time
        _dateError.value=null
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        _selectedDateFormatted.value = dateFormat.format(selectedDate.value)


    }

    fun saveSale() {
        if(!validateFields()) return
        val saleQuantity = quantity.value!!.toDouble()
        val salePrice = price.value!!.toDouble()

        val totalAmount = saleQuantity * salePrice

        val sale = MilkSale(
            id = System.currentTimeMillis(), // Generates a unique ID
            quantity = saleQuantity,
            pricePerUnit = salePrice,
            totalAmount = totalAmount,
            date = selectedDate.value?.time ?: 0L
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertMilkSale(sale).runCatching {
                _message.postValue("saved")
                }

            } catch (e: Exception) {
                _message.postValue("Error while saving data $e")
            }
        }
    }

    private fun validateFields(): Boolean {
        if (quantity.value.isNullOrEmpty()) {
            _quantityError.value = "Please enter a quantity."
            return false
        }
        if (price.value.isNullOrEmpty()) {
            _priceError.value = "Please enter a price."
            return false
        }
        if(_selectedDateFormatted.value.isNullOrEmpty()){
            _dateError.value="Please enter a Date."
            return false
        }
        clearErrors()
        return true
    }

    fun clearErrors(){
        _priceError.value=null
        _dateError.value=null
        _quantityError.value=null
    }

    }
class RecordSalesViewModelFactory(private val repository: MilkSaleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordSalesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordSalesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


