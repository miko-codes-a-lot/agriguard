package com.example.agriguard.modules.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxField(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.
        width(150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Checkbox(checked = value, onCheckedChange = onChange)
        Text(label, modifier = Modifier.padding(start = 1.dp))
    }
}