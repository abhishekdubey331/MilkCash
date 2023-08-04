package com.animall.repositories

import com.animall.Daos.MilkSaleDao
import com.animall.Entity.MilkSaleEntity
import com.animall.Models.MilkSale

class MilkSaleRepository(private val milkSaleDao: MilkSaleDao) {

    suspend fun insertMilkSale(milkSale: MilkSale) {
        try {
            val milkSaleEntity = MilkSaleEntity(
                id = milkSale.id,
                quantity = milkSale.quantity,
                pricePerUnit = milkSale.pricePerUnit,
                totalAmount = milkSale.totalAmount,
                date = milkSale.date
            )
            milkSaleDao.insert(milkSaleEntity)
        } catch (e: Exception) {
            throw e
        }
    }
    suspend fun getSalesBetweenDates(startDate: Long, endDate: Long): List<MilkSale> {
        try {
            val milkSaleEntities = milkSaleDao.getSalesBetweenDates(startDate, endDate)
            return milkSaleEntities.map { entity ->
                MilkSale(
                    id = entity.id,
                    quantity = entity.quantity,
                    pricePerUnit = entity.pricePerUnit,
                    totalAmount = entity.totalAmount,
                    date = entity.date
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

}
