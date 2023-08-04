package com.animall.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="milk_sale_table")
data class MilkSaleEntity(
    @PrimaryKey(autoGenerate = true)val id: Long, // Unique identifier for each sale
    @ColumnInfo(name = "quantity")val quantity: Double,
    @ColumnInfo(name = "pricePerUnit")val pricePerUnit: Double,
    @ColumnInfo(name = "totalAmount") val totalAmount: Double,
    @ColumnInfo(name = "date") val date: Long
)