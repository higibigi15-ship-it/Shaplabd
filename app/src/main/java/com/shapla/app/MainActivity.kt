package com.shapla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ShaplaGreen = Color(0xFF087F4F)
private val ShaplaLight = Color(0xFFF2FAF5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ShaplaHome()
            }
        }
    }
}

@Composable
fun ShaplaHome() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ShaplaLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "❀",
                fontSize = 76.sp,
                color = ShaplaGreen
            )

            Text(
                text = "শাপলা",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = ShaplaGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "কাজ, সেবা, সব একসাথে।",
                fontSize = 17.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShaplaGreen
                )
            ) {
                Text(
                    text = "শুরু করুন",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Designed & Owned by\nMohammad Israfil Hossain",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
