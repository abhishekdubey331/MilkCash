package com.animall.repositories

import com.animall.Daos.MilkSaleDao
import com.animall.models.MilkSaleEntity

class MilkSaleRepository(private val milkSaleDao: MilkSaleDao) {

    suspend fun insertMilkSale(milkSaleEntity: MilkSaleEntity) {
        try {
            milkSaleDao.insert(milkSaleEntity)
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getSalesBetweenDates(startDate: Long, endDate: Long): List<MilkSaleEntity> {
        try {
            return milkSaleDao.getSalesBetweenDates(startDate, endDate)
        } catch (e: Exception) {
            throw e
        }
    }

}
