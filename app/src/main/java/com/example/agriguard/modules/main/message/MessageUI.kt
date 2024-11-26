package com.example.agriguard.modules.main.message

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDefaults.contentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.agriguard.R
import com.example.agriguard.modules.main.message.model.Message


@Preview(showSystemUi = true)
@Composable
fun MessageUIPreview() {
    MessageUI(
        navController = rememberNavController(),
    )
}

@Composable
fun MessageUI(navController: NavController) {
    var messages by remember { mutableStateOf(listOf(
        Message("user1", "Message"),
        Message(senderId = "user2", content = "Message")
    )) }
    fun onSend(newMessage: String) {
        messages = messages + Message("user1", newMessage)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MessageContainer(message = messages, onSend = ::onSend)
    }
}

@Composable
fun MessageContainer(
    message: List<Message>,
    onSend: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(bottom = 40.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            reverseLayout = true
        ) {
            items(message) { message ->
                MessageView(message = message)
            }
        }
        MessageInputField(onSend = onSend)
    }
}

@Composable
fun MessageView(message: Message) {
    val isSender = message.senderId == "user1"

    val (containerColor, contentColor) =
        if (isSender) {
            Color(0xFF136204) to Color(0xFFFFFFFF)
        } else {
            Color(0xFFcccccf) to Color.Black
        }

    val horizontalArrangement =
        if (isSender)
            Arrangement.End
        else
            Arrangement.Start
    Row(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 10.dp, bottom = 10.dp),
            shape = RoundedCornerShape(12.dp),
            color = containerColor,
            contentColor = contentColor
        ) {
            Column(modifier = Modifier
                .padding(12.dp)
            ) {
                Text(
                    text = "$isSender",
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Visible
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "08:30 A.M",
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.38f)
                )
            }
        }
    }
}

@Composable
fun MessageInputField(
    onSend: (String) -> Unit
) {
    val messageText = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFF136204), RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = messageText.value,
            onValueChange = { messageText.value = it },
            placeholder = { Text("Type your message...") },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF136204),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.send_24),
            contentDescription = "Send",
            modifier = Modifier
                .padding(8.dp)
                .size(25.dp)
                .clickable {
                    if (messageText.value.isNotEmpty()) {
                        onSend(messageText.value)
                        messageText.value = ""
                    }
                },
            tint = Color(0xFF136204)
        )
        Spacer(modifier = Modifier.padding(end = 10.dp))
    }
}
