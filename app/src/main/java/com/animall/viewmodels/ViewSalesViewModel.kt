package com.animall.viewmodels


import androidx.lifecycle.*
import com.animall.Entity.MilkSaleEntity
import com.animall.Models.MilkSale
import com.animall.repositories.MilkSaleRepository
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

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

    private val _milkSaleList: MutableLiveData<List<MilkSale>> = MutableLiveData()
    val milkSaleList: LiveData<List<MilkSale>> = _milkSaleList


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

        if(startTime)  {
            val calendar = Calendar.getInstance().apply {
                set(year, monthOfYear, dayOfMonth)
            }
            startSelectedDate = calendar.time
        }
        else  {
            val calendar = Calendar.getInstance().apply {
                set(year, monthOfYear, dayOfMonth)
            }
            endSelectedDate=calendar.time
        }
    }
    private fun getSalesBetweenDates(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            try {
                val sales = repository.getSalesBetweenDates(startDate, endDate)
                calculateSalesAndRevenue(sales)

            } catch (e: Exception) {
                _message.value = "Error getting sales: ${e.message}"
            }
        }
    }
    fun calculateSalesAndRevenue(sales:List<MilkSale>) {
        val totalSales = sales.sumOf { it.quantity }
        val totalRevenue = sales.sumOf { it.totalAmount }
        _totalSales.value = totalSales
        _totalRevenue.value = totalRevenue
        if(sales.isEmpty()){
            _message.postValue("there seems to be no data between the given dates")
            _milkSaleList.value= mutableListOf()
        }
        else{
            _message.postValue("data fetched successfully")
            _milkSaleList.value=sales
        }

            }
    fun clearAllData(){
        startSelectedDate=null;
        endSelectedDate=null;
        _message.value=""
        _totalSales.value=0.0
        _totalRevenue.value=0.0
        _milkSaleList.value=  mutableListOf()
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


