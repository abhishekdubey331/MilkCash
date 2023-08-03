package com.animall.Activities

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.*


class DatePickerDialogBox(private val context: Context) {

    fun showDatePicker(editText: EditText, isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val listener = context as? DatePickerListener
                listener?.onDateSelected(year, month, dayOfMonth, editText, isStartDate)
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.show()
    }
}
interface DatePickerListener {
    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int, editText: EditText, isStartDate: Boolean)
}