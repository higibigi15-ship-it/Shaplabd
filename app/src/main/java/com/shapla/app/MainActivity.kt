
package com.shapla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ShaplaGreen = Color(0xFF087F4F)
private val ScreenBackground = Color(0xFFF7F7F7)
private val TextDark = Color(0xFF343434)
private val BorderGrey = Color(0xFFBDBDBD)
private val ErrorColor = Color(0xFF8B2525)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ShaplaLoginScreen()
            }
        }
    }
}

@Composable
fun ShaplaLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(76.dp)) {
        val cx = size.width / 2f
        val cy = size.height * 0.53f

        // Lotus petals
        val petalColors = listOf(
            Color(0xFF16A064),
            Color(0xFF087F4F),
            Color(0xFF14945B),
            Color(0xFF087F4F),
            Color(0xFF16A064)
        )

        for (i in -2..2) {
            val path = Path().apply {
                moveTo(cx, cy + size.height * 0.28f)
                quadraticTo(
                    cx + i * size.width * 0.15f,
                    cy - size.height * 0.62f,
                    cx + i * size.width * 0.19f,
                    cy - size.height * 0.05f
                )
                quadraticTo(
                    cx + i * size.width * 0.12f,
                    cy + size.height * 0.20f,
                    cx,
                    cy + size.height * 0.28f
                )
                close()
            }

            drawPath(
                path = path,
                color = petalColors[i + 2]
            )
        }

        // Base leaves
        val leaf = Path().apply {
            moveTo(cx, cy + size.height * 0.18f)
            quadraticTo(
                cx - size.width * 0.48f,
                cy - size.height * 0.03f,
                cx - size.width * 0.43f,
                cy + size.height * 0.30f
            )
            quadraticTo(
                cx - size.width * 0.18f,
                cy + size.height * 0.44f,
                cx,
                cy + size.height * 0.18f
            )
            close()
        }

        drawPath(leaf, Color(0xFF087F4F))

        val rightLeaf = Path().apply {
            moveTo(cx, cy + size.height * 0.18f)
            quadraticTo(
                cx + size.width * 0.48f,
                cy - size.height * 0.03f,
                cx + size.width * 0.43f,
                cy + size.height * 0.30f
            )
            quadraticTo(
                cx + size.width * 0.18f,
                cy + size.height * 0.44f,
                cx,
                cy + size.height * 0.18f
            )
            close()
        }

        drawPath(rightLeaf, Color(0xFF14945B))
    }
}

@Composable
fun ShaplaLoginScreen() {
    var phone by remember {
        mutableStateOf("+880 1303556793")
    }

    var errorMessage by remember {
        mutableStateOf(
            "OTP পাঠানো যায়নি: An internal error has occurred.\n[BILLING_NOT_ENABLED]"
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ScreenBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ShaplaLogo()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "শাপলা",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = ShaplaGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "কাজ, সেবা, সব একসাথে।",
                fontSize = 15.sp,
                color = TextDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("মোবাইল নম্বর") },
                placeholder = { Text("+880 1303556793") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                shape = RoundedCornerShape(13.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ShaplaGreen,
                    unfocusedBorderColor = BorderGrey,
                    focusedLabelColor = ShaplaGreen,
                    cursorColor = ShaplaGreen
                )
            )

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = {
                    errorMessage =
                        "OTP পাঠানো যায়নি: An internal error has occurred.\n" +
                        "[BILLING_NOT_ENABLED]"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(13.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShaplaGreen
                )
            ) {
                Text(
                    text = "SMS OTP পাঠান",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                lineHeight = 19.sp,
                color = ErrorColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = "Designed & Owned by",
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF999999)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Mohammad Israfil Hossain",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Cursive,
                color = TextDark,
                textAlign = TextAlign.Center
            )
        }
    }
}
