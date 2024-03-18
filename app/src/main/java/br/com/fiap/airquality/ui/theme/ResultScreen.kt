import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.airquality.R

@Composable
fun ResultScreen(city: String, state: String, aqi: Int, navController: NavHostController) {
    val colorAqi = getColorForAqi(aqi)
    val textAqi = getTextForAqi(aqi)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .clickable { /* Ação ao clicar na caixa */ }
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Localização",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally)
                    .width(285.dp)
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loc),
                    contentDescription = "Ícone de localização",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 16.dp)
                )
                Text(
                    text = "$city,$state",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 32.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .border(width = 6.dp, color = colorAqi, shape = RoundedCornerShape(100.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(100.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "IQA: $aqi",
                        color = colorAqi,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = textAqi,
                        color = colorAqi,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Button(
            onClick = {
                navController.navigate("DetailsScreen")
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "O QUE É IQA ?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


fun getColorForAqi(aqi: Int): Color {
    return when {
        aqi <= 30 -> Color(3, 168, 233, 195)// Bom
        aqi in 30..50 -> Color.Green // Moderado
        aqi in 51..100 -> Color(255, 193, 7, 195)// Não saudável para grupos sensíveis
        aqi in 101..150 -> Color(255, 85, 0, 255) // Não saudável
        aqi in 151..200 -> Color.Red // Muito não saudável
        else -> Color(128, 0, 255)// Perigoso
    }
}

fun getTextForAqi(aqi: Int): String {
    return when {
        aqi <= 30 -> "Excelente" // Bom
        aqi in 30..50 -> "Bom" // Moderado
        aqi in 51..100 -> "Moderado" // Não saudável para grupos sensíveis
        aqi in 101..150 -> "Baixo" // Não saudável
        aqi in 151..200 -> "Insalubre" // Muito não saudável
        else -> "Perigoso"// Perigoso
    }
}