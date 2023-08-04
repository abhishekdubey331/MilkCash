package com.animall.Models

data class MilkSale(
    val id: Long,
    val quantity: Double,
    val pricePerUnit: Double,
    val totalAmount: Double,
    val date: Long
)