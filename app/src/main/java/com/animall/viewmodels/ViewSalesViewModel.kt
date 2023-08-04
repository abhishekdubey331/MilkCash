package com.animall.viewmodels


import androidx.lifecycle.*
import com.animall.models.MilkSaleEntity
import com.animall.repositories.MilkSaleRepository
import kotlinx.coroutines.launch
import java.util.*

class ViewSalesViewModel(private val repository: MilkSaleRepository): ViewModel() {


   private var startSelectedDate :Date?=null
    private var endSelectedDate :Date?=null
    private val _totalSales = MutableLiveData<Double>(0.0)
    val totalSales: LiveData<Double>
        get() = _totalSales

    private val _totalRevenue = MutableLiveData<Double>(0.0)
    val totalRevenue: LiveData<Double>
        get() = _totalRevenue






    private val _message = MutableLiveData<String>("")
    val message: LiveData<String>
        get() = _message


     fun validatedDatesAndGetData():Boolean{
        if (startSelectedDate == null || endSelectedDate == null) {
            _message.value = "Please select both start and end dates"
            return false
        }

        if (startSelectedDate!!.after(endSelectedDate)) {
            _message.value = "Start date cannot be after end date"
            return false
        }
        getSalesBetweenDates(startSelectedDate!!.time,endSelectedDate!!.time)


         return true
    }



    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int,startTime:Boolean) {
        val calendar = Calendar.getInstance().apply {
            set(year, monthOfYear, dayOfMonth)
        }
        if(startTime) startSelectedDate = calendar.time
        else endSelectedDate=calendar.time
    }
    fun getSalesBetweenDates(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            try {
                val sales = repository.getSalesBetweenDates(startDate, endDate)
                calculateSalesAndRevenue(sales)

            } catch (e: Exception) {
                _message.value = "Error getting sales: ${e.message}"
            }
        }
    }
    fun calculateSalesAndRevenue(sales:List<MilkSaleEntity>) {
        val totalSales = sales.sumOf { it.quantity }
        val totalRevenue = sales.sumOf { it.totalAmount }
        _totalSales.value = totalSales
        _totalRevenue.value = totalRevenue
        _message.postValue("data fetched successfully!")
            }
    fun clearAllData(){
        startSelectedDate=null;
        endSelectedDate=null;
        _message.value=""
        _totalSales.value=0.0
        _totalRevenue.value=0.0
    }
}
class ViewSalesViewModelFactory(private val repository: MilkSaleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewSalesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewSalesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


