
package com.shapla.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.drawscope.rotate

private val ShaplaBackground = Color(0xFFF4F9F5)
private val ShaplaGreen = Color(0xFF087F4F)
private val ShaplaDark = Color(0xFF292929)
private val ShaplaGrey = Color(0xFF777777)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ShaplaWelcomeScreen(
                    onGetStarted = {
                        // Connect the next screen here later.
                    }
                )
            }
        }
    }
}

@Composable
fun ShaplaWelcomeScreen(
    onGetStarted: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val compactScreen = configuration.screenHeightDp < 650

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ShaplaBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ShaplaBackground)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            ShaplaFlowerLogo(
                modifier = Modifier.size(if (compactScreen) 82.dp else 104.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "শাপলা",
                fontSize = if (compactScreen) 34.sp else 40.sp,
                fontWeight = FontWeight.Bold,
                color = ShaplaGreen,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "কাজ, সেবা, সব একসাথে।",
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = ShaplaDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(34.dp))

            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShaplaGreen,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Text(
                    text = "শুরু করুন",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Designed & Owned by",
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                color = ShaplaGrey,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Mohammad Israfil Hossain",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ShaplaGrey,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ShaplaFlowerLogo(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val petalWidth = size.width * 0.27f
        val petalHeight = size.height * 0.43f

        for (angle in listOf(0f, 72f, 144f, 216f, 288f)) {
            rotate(degrees = angle, pivot = center) {
                val path = Path().apply {
                    moveTo(center.x, center.y + size.height * 0.12f)
                    quadraticTo(
                        center.x - petalWidth,
                        center.y - petalHeight * 0.45f,
                        center.x,
                        center.y - petalHeight
                    )
                    quadraticTo(
                        center.x + petalWidth,
                        center.y - petalHeight * 0.45f,
                        center.x,
                        center.y + size.height * 0.12f
                    )
                    close()
                }

                drawPath(
                    path = path,
                    color = ShaplaGreen
                )
                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.8f),
                    style = Stroke(width = 1.2.dp.toPx())
                )
            }
        }

        drawCircle(
            color = Color(0xFFFFD166),
            radius = size.minDimension * 0.065f,
            center = center
        )
    }
}
