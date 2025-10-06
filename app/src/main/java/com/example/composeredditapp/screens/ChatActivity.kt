package com.example.composeredditapp.screens

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeredditapp.R
import com.example.composeredditapp.databinding.ActivityChatBinding

class ChatActivity : ComponentActivity() {
    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.composeButton.setContent {
            MaterialTheme {
                ComposeButton { showToast() }
            }
        }
    }


    private fun showToast() {
        Toast.makeText(this,"Imaginary chat started!", Toast.LENGTH_SHORT).show()
    }
}

@Composable
private fun ComposeButton(onButtonClick: () -> Unit) {
    val buttonColor = buttonColors(
        containerColor = Color(0xFF006837),
        contentColor = Color.White
    )

    Button(
        onClick = onButtonClick,
        elevation = null,
        shape = RoundedCornerShape(corner = CornerSize(24.dp)),
        contentPadding = PaddingValues(
            start = 32.dp,
            end = 32.dp
        ),
        colors = buttonColor,
        modifier = Modifier.height(48.dp)
    ) {
        Text(
            text = "Start chatting".uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}