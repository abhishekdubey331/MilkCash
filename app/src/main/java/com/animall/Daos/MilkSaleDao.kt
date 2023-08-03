package com.animall.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.animall.models.MilkSale
import kotlinx.coroutines.flow.Flow

@Dao
interface MilkSaleDao {
    @Insert
     suspend fun insert(milkSale: MilkSale)

    @Query("DELETE FROM milk_sale_table WHERE date >= :startDate AND date <= :endDate")
    suspend fun deleteSalesBetweenDates(startDate: Long, endDate: Long)

    @Query("SELECT * FROM milk_sale_table WHERE date >= :startDate AND date <= :endDate")
    suspend fun getSalesBetweenDates(startDate: Long, endDate: Long): List<MilkSale>

    @Query("SELECT * FROM milk_sale_table")
     fun getAllSales(): List<MilkSale>

}
