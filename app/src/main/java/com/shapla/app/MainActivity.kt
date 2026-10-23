package com.shapla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

private val Green = Color(0xFF087F4F)
private val Background = Color(0xFFF4F9F5)
private val Dark = Color(0xFF292929)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ShaplaLoginScreen(
                    activity = this,
                    auth = FirebaseAuth.getInstance()
                )
            }
        }
    }
}

@Composable
fun ShaplaLoginScreen(
    activity: ComponentActivity,
    auth: FirebaseAuth
) {
    var phone by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var otpMode by remember { mutableStateOf(false) }
    var verificationId by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var signedIn by remember {
        mutableStateOf(auth.currentUser != null)
    }
    var message by remember { mutableStateOf("") }

    fun signInWithCredential(credential: PhoneAuthCredential) {
        loading = true
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                loading = false
                if (task.isSuccessful) {
                    signedIn = true
                    message = "লগইন সফল হয়েছে।"
                } else {
                    message = if (
                        task.exception is FirebaseAuthInvalidCredentialsException
                    ) {
                        "OTP ভুল বা মেয়াদ শেষ। আবার চেষ্টা করুন।"
                    } else {
                        "লগইন হয়নি। ইন্টারনেট ও Firebase সেটিংস পরীক্ষা করুন।"
                    }
                }
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        if (signedIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("❀", fontSize = 64.sp, color = Green)
                Text(
                    "শাপলা",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "লগইন সফল হয়েছে!",
                    fontSize = 20.sp,
                    color = Dark
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "তোমার অ্যাকাউন্ট: ${
                        auth.currentUser?.phoneNumber ?: ""
                    }",
                    textAlign = TextAlign.Center,
                    color = Dark
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    "এটি আপাতত লগইন সফলতার স্ক্রিন। " +
                        "শাপলার মূল Home, Vault, Messenger ও Job Alert " +
                        "এখনো আলাদাভাবে তৈরি করতে হবে।",
                    textAlign = TextAlign.Center,
                    color = Dark
                )
                Spacer(Modifier.height(24.dp))
                OutlinedButton(onClick = {
                    auth.signOut()
                    signedIn = false
                    phone = ""
                    otp = ""
                    verificationId = null
                    otpMode = false
                    message = ""
                }) {
                    Text("লগআউট")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("❀", fontSize = 64.sp, color = Green)
                Text(
                    "শাপলা",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    if (otpMode) "আপনার OTP কোড লিখুন"
                    else "কাজ, সেবা, সব একসাথে।",
                    fontSize = 16.sp,
                    color = Dark,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(32.dp))

                if (!otpMode) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it.filter { ch -> ch in '0'..'9' }
                                .take(10)
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loading,
                        label = { Text("মোবাইল নম্বর") },
                        placeholder = { Text("1XXXXXXXXX") },
                        prefix = { Text("+880 ") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (
                                phone.length != 10 ||
                                !phone.startsWith("1")
                            ) {
                                message =
                                    "সঠিক ১০ সংখ্যার বাংলাদেশি মোবাইল নম্বর দিন।"
                            } else {
                                loading = true
                                message = "SMS OTP পাঠানোর চেষ্টা চলছে..."
                                auth.setLanguageCode("bn")

                                val callbacks =
                                    object :
                                        PhoneAuthProvider
                                            .OnVerificationStateChangedCallbacks() {

                                        override fun onVerificationCompleted(
                                            credential: PhoneAuthCredential
                                        ) {
                                            signInWithCredential(credential)
                                        }

                                        override fun onVerificationFailed(
                                            e: FirebaseException
                                        ) {
                                            loading = false
                                            message =
                                                "OTP পাঠানো যায়নি: " +
                                                    (e.localizedMessage
                                                        ?: "Firebase সেটিংস পরীক্ষা করুন।")
                                        }

                                        override fun onCodeSent(
                                            id: String,
                                            token: PhoneAuthProvider
                                                .ForceResendingToken
                                        ) {
                                            verificationId = id
                                            otpMode = true
                                            loading = false
                                            message =
                                                "SMS-এ পাঠানো ৬ সংখ্যার OTP লিখুন।"
                                        }

                                        override fun onCodeAutoRetrievalTimeOut(
                                            id: String
                                        ) {
                                            verificationId = id
                                            loading = false
                                            if (!signedIn) {
                                                message =
                                                    "স্বয়ংক্রিয় শনাক্তকরণের সময় শেষ। " +
                                                        "SMS থেকে OTP লিখুন।"
                                            }
                                        }
                                    }

                                val options =
                                    PhoneAuthOptions.newBuilder(auth)
                                        .setPhoneNumber("+880$phone")
                                        .setTimeout(
                                            60L,
                                            TimeUnit.SECONDS
                                        )
                                        .setActivity(activity)
                                        .setCallbacks(callbacks)
                                        .build()

                                PhoneAuthProvider.verifyPhoneNumber(options)
                            }
                        },
                        enabled = !loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        )
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "SMS OTP পাঠান",
                                fontSize = 16.sp
                            )
                        }
                    }
                } else {
                    Text(
                        "+880 $phone",
                        color = Green,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            otp = it.filter { ch -> ch in '0'..'9' }
                                .take(6)
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loading,
                        label = { Text("৬ সংখ্যার OTP") },
                        placeholder = { Text("••••••") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val id = verificationId
                            if (otp.length != 6) {
                                message = "৬ সংখ্যার OTP লিখুন।"
                            } else if (id == null) {
                                message =
                                    "OTP verification session পাওয়া যায়নি। " +
                                        "আবার SMS OTP পাঠান।"
                            } else {
                                loading = true
                                message = "OTP যাচাই করা হচ্ছে..."
                                val credential =
                                    PhoneAuthProvider.getCredential(id, otp)
                                signInWithCredential(credential)
                            }
                        },
                        enabled = !loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        )
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("OTP যাচাই করুন", fontSize = 17.sp)
                        }
                    }

                    TextButton(
                        enabled = !loading,
                        onClick = {
                            otpMode = false
                            otp = ""
                            verificationId = null
                            message = ""
                        }
                    ) {
                        Text("নম্বর পরিবর্তন করুন", color = Green)
                    }
                }

                if (message.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        message,
                        color = if (
                            message.contains("যায়নি") ||
                            message.contains("ভুল") ||
                            message.contains("পাওয়া")
                        ) {
                            Color(0xFFB3261E)
                        } else {
                            Green
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(40.dp))
                Text(
                    "Designed & Owned by",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    "Mohammad Israfil Hossain",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}