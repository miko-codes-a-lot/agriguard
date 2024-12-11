package com.example.agriguard.modules.shared.ui

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
fun DatePickerField(
    context: Context,
    label: String,
    value: String?,
    onChange: (String) -> Unit
) {
    var displayedDate by rememberSaveable { mutableStateOf("") }

    val displayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val saveDateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    LaunchedEffect(value) {
        displayedDate = if (value.isNullOrEmpty()) "" else {
            try {
                val parsedDate = saveDateFormat.parse(value) // Parse ISO date
                displayDateFormat.format(parsedDate!!) // Convert to friendly format
            } catch (e: ParseException) {
                "" // Handle invalid date format gracefully
            }
        }
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            displayedDate = displayDateFormat.format(calendar.time) // Friendly date
            onChange(saveDateFormat.format(calendar.time)) // ISO 8601 date
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Text("$label: $displayedDate")
    Button(
        onClick = {
            datePickerDialog.show()
        }
    ) {
        Text("Pick a date")
    }
}