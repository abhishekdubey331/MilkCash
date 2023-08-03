package com.animall.viewmodels
import android.util.Log
import androidx.lifecycle.*
import com.animall.models.MilkSale
import com.animall.repositories.MilkSaleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RecordSalesViewmodel(private val repository: MilkSaleRepository): ViewModel() {
    val quantity = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val datePickerVisible = MutableLiveData<Boolean>()
    val selectedDate = MutableLiveData<Date>()

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

    init {
        datePickerVisible.value = false
    }

    fun showDatePicker() {
        datePickerVisible.value = true
    }

    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, monthOfYear, dayOfMonth)
        }
        selectedDate.value = calendar.time
        datePickerVisible.value = false
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        _selectedDateFormatted.value = dateFormat.format(selectedDate.value)
        datePickerVisible.value = false

    }


    fun saveSale() {
        if (quantity.value.isNullOrEmpty()) {
            _quantityError.value = "Please enter a quantity."
            return
        }


        if (price.value.isNullOrEmpty()) {
            _priceError.value = "Please enter a price."
            return
        }

        if(_selectedDateFormatted.value.equals("Date:")){
            _dateError.value="Please enter a Date."
            return
        }

        clearErrors()

        val saleQuantity = quantity.value!!.toDouble()
        val salePrice = price.value!!.toDouble()

        val totalAmount = saleQuantity * salePrice

        val sale = MilkSale(
            id = System.currentTimeMillis(), // Generate a unique ID
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
                _message.postValue("Error while saving data ${e.toString()}")
            }
        }
    }
    fun deleteSalesBetweenDates(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            try {
                repository.deleteSalesBetweenDates(startDate, endDate)
                _message.value = "Sales deleted between selected dates"
            } catch (e: Exception) {
                _message.value = "Error deleting sales: ${e.message}"
            }
        }
    }
    fun getSalesBetweenDates(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            try {
                val sales = repository.getSalesBetweenDates(startDate, endDate)
            } catch (e: Exception) {
                _message.value = "Error getting sales: ${e.message}"
            }
        }
    }
    fun getAllSales() {
        CoroutineScope(Dispatchers.IO).launch {
            val sales = repository.getAllSales()
            Log.d("Database", "All Milk Sales: $sales")
        }
    }
    fun clearErrors(){
        _priceError.value=null
        _dateError.value=null
        _quantityError.value=null
    }

    }
class RecordSalesViewModelFactory(private val repository: MilkSaleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordSalesViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordSalesViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


