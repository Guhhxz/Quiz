package com.example.quiz.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quiz.R

@Composable
fun ResultScreen(
    navController: NavController,
    acertos: Int = 1,
    totalPerguntas: Int = 3
) {
    val mensagem = when {
        acertos == totalPerguntas -> "Perfeito!"
        acertos >= totalPerguntas / 2.0 -> "Bom trabalho!"
        else -> "Tente de novo!"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.quiz),
            contentDescription = "Logo Quiz",
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF86D8F7))
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = VerdeBadge,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFF2B2B2B))
            ) {
                Text(
                    text = mensagem,
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 14.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Você acertou $acertos de $totalPerguntas perguntas",
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("start") {
                    popUpTo("start") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7E04F)),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color(0xFF2B2B2B)),
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(56.dp)
        ) {
            Text(
                text = "JOGAR NOVAMENTE",
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}