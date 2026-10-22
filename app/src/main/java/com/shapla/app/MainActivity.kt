
package com.shapla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF087F4F)
private val Background = Color(0xFFF4F9F5)
private val Dark = Color(0xFF292929)

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
fun ShaplaLoginScreen() {
    var phone by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var otpMode by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "❀",
                fontSize = 64.sp,
                color = Green
            )

            Text(
                text = "শাপলা",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Green
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (otpMode) {
                    "আপনার OTP কোড লিখুন"
                } else {
                    "কাজ, সেবা, সব একসাথে।"
                },
                fontSize = 16.sp,
                color = Dark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (!otpMode) {
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it.filter { ch -> ch.isDigit() }
                            .take(10)
                        message = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("মোবাইল নম্বর") },
                    placeholder = { Text("1XXXXXXXXX") },
                    prefix = { Text("+880 ") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (phone.length == 10 &&
                            phone.startsWith("1")
                        ) {
                            otpMode = true
                            message =
                                "ডেমো মোড: বাস্তব SMS OTP এখনো যুক্ত হয়নি।"
                        } else {
                            message =
                                "সঠিক ১০ সংখ্যার বাংলাদেশি মোবাইল নম্বর দিন।"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    )
                ) {
                    Text(
                        text = "OTP-এর জন্য এগিয়ে যান",
                        fontSize = 16.sp
                    )
                }
            } else {
                Text(
                    text = "+880 $phone",
                    color = Green,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = {
                        otp = it.filter { ch -> ch.isDigit() }
                            .take(6)
                        message = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("৬ সংখ্যার OTP") },
                    placeholder = { Text("••••••") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        message =
                            "বাস্তব OTP যাচাই এখনো সেটআপ করা হয়নি।"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    )
                ) {
                    Text(
                        text = "OTP যাচাই করুন",
                        fontSize = 17.sp
                    )
                }

                TextButton(
                    onClick = {
                        otpMode = false
                        otp = ""
                        message = ""
                    }
                ) {
                    Text(
                        text = "নম্বর পরিবর্তন করুন",
                        color = Green
                    )
                }
            }

            if (message.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = message,
                    color = if (otpMode &&
                        message.startsWith("বাস্তব")
                    ) Dark else Green,
                    textAlign = TextAlign.Center,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Designed & Owned by",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Text(
                text = "Mohammad Israfil Hossain",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}
