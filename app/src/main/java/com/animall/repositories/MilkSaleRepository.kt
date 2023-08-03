package com.animall.repositories

import androidx.lifecycle.LiveData
import com.animall.Daos.MilkSaleDao
import com.animall.models.MilkSale
import kotlinx.coroutines.flow.Flow
import java.util.*

class MilkSaleRepository(private val milkSaleDao: MilkSaleDao) {

    suspend fun insertMilkSale(milkSale: MilkSale) {
        try {
            milkSaleDao.insert(milkSale)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteSalesBetweenDates(startDate: Long, endDate: Long) {
        try {
            milkSaleDao.deleteSalesBetweenDates(startDate, endDate)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getSalesBetweenDates(startDate: Long, endDate: Long): List<MilkSale> {
        try {
            return milkSaleDao.getSalesBetweenDates(startDate, endDate)
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getAllSales(): List<MilkSale> {
        try {
            return milkSaleDao.getAllSales()
        } catch (e: Exception) {
            throw e
        }
    }

}
