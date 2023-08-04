package com.animall.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.animall.Models.MilkSale
import com.animall.R
import java.text.SimpleDateFormat
import java.util.*

class ViewSalesAdaptor {
}
class MilkSaleAdapter(private val milkSales: List<MilkSale>) : RecyclerView.Adapter<MilkSaleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dtadpt)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTxt)
        val priceTextView: TextView = itemView.findViewById(R.id.priceText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_milksale, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val milkSale = milkSales[position]
        holder.dateTextView.text = formatDate(milkSale.date)
        holder.quantityTextView.text = milkSale.quantity.toString()
        holder.priceTextView.text=milkSale.pricePerUnit.toString()
    }

    override fun getItemCount(): Int {
        return milkSales.size
    }
    fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}
