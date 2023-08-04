package com.animall.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.animall.Entity.MilkSaleEntity

@Dao
interface MilkSaleDao {
    @Insert
     suspend fun insert(milkSaleEntity: MilkSaleEntity)
    @Query("SELECT * FROM milk_sale_table WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    suspend fun getSalesBetweenDates(startDate: Long, endDate: Long): List<MilkSaleEntity>

    @Query("SELECT * FROM milk_sale_table")
    suspend fun getAllSales(): List<MilkSaleEntity>

}
