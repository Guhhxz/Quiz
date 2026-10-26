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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quiz.R

val RosaFundo = Color(0xFFEB96CC)
val VerdeBadge = Color(0xFF8ED3A6)

data class Pergunta(
    val texto: String,
    val opcoes: List<String>,
    val correta: Int
)

val perguntas = listOf(
    Pergunta(
        texto = "Qual é a capital da França?",
        opcoes = listOf("Londres", "Berlim", "Paris", "Madri"),
        correta = 2
    ),
    Pergunta(
        texto = "Qual planeta é conhecido como planeta vermelho?",
        opcoes = listOf("Vênus", "Marte", "Júpiter", "Saturno"),
        correta = 1
    ),
    Pergunta(
        texto = "Qual é o maior oceano do mundo?",
        opcoes = listOf("Atlântico", "Índico", "Pacífico", "Ártico"),
        correta = 2
    )
)

@Composable
fun QuizScreen(navController: NavController) {
    var indice by rememberSaveable { mutableIntStateOf(0) }
    var acertos by rememberSaveable { mutableIntStateOf(0) }
    var selecionada by rememberSaveable { mutableStateOf<Int?>(null) }

    val pergunta = perguntas[indice]
    val respondeu = selecionada != null
    val ultima = indice == perguntas.lastIndex

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RosaFundo)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.quiz),
            contentDescription = "Logo Quiz",
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            color = VerdeBadge,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(2.dp, Color(0xFF2B2B2B))
        ) {
            Text(
                text = "Pergunta ${indice + 1} de ${perguntas.size}",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = pergunta.texto,
                    fontSize = 24.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                pergunta.opcoes.forEachIndexed { index, opcao ->
                    val ehCorreta = index == pergunta.correta
                    val ehEscolhida = index == selecionada

                    // Depois de responder: a correta fica verde e a errada escolhida fica vermelha
                    val corBorda = when {
                        respondeu && ehCorreta -> Color(0xFF2E7D32)
                        respondeu && ehEscolhida -> Color(0xFFC62828)
                        else -> Color(0xFFDDDDDD)
                    }
                    val corFundo = when {
                        respondeu && ehCorreta -> Color(0xFFB9F6CA)
                        respondeu && ehEscolhida -> Color(0xFFFFCDD2)
                        else -> Color(0xFFFFD6EB)
                    }

                    OutlinedButton(
                        onClick = {
                            if (!respondeu) {
                                selecionada = index
                                if (ehCorreta) acertos++
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, corBorda),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = corFundo)
                    ) {
                        Text(
                            text = opcao,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (respondeu) {
                    Button(
                        onClick = {
                            if (ultima) {
                                navController.navigate("result/$acertos/${perguntas.size}") {
                                    popUpTo("quiz") { inclusive = true }
                                }
                            } else {
                                indice++
                                selecionada = null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Color(0xFF2B2B2B)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF7E04F),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = if (ultima) "VER RESULTADO" else "PRÓXIMA",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}