import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.airquality.R
import br.com.fiap.airquality.ui.theme.ApiResponse
import br.com.fiap.airquality.ui.theme.ApiService
import br.com.fiap.airquality.ui.theme.Estados
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavHostController) {
    var estadoSelecionado by remember { mutableStateOf("") }
    var cidadeSelecionada by remember { mutableStateOf("") }
    var isExpandedEstado by remember { mutableStateOf(false) }
    var isExpandedCidade by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 50.dp, start = 40.dp)
            ) {
                Text("Escolha seu estado", fontWeight = FontWeight.Bold, color = Color.White)
                ExposedDropdownMenuBox(
                    expanded = isExpandedEstado,
                    onExpandedChange = { isExpandedEstado = !isExpandedEstado }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = estadoSelecionado,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedEstado) }
                    )
                    ExposedDropdownMenu(
                        expanded = isExpandedEstado,
                        onDismissRequest = { isExpandedEstado = false }
                    ) {
                        Estados.listaEstados.forEachIndexed { index, estado ->
                            DropdownMenuItem(
                                text = { Text(text = estado.nome) },
                                onClick = {
                                    estadoSelecionado = estado.nome
                                    isExpandedEstado = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Text("Escolha sua cidade", fontWeight = FontWeight.Bold, color = Color.White)
                ExposedDropdownMenuBox(
                    expanded = isExpandedCidade,
                    onExpandedChange = { isExpandedCidade = !isExpandedCidade }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = cidadeSelecionada,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedCidade) }
                    )
                    ExposedDropdownMenu(
                        expanded = isExpandedCidade,
                        onDismissRequest = { isExpandedCidade = false }
                    ) {
                        Estados.listaEstados.firstOrNull { it.nome == estadoSelecionado }
                            ?.cidades?.forEachIndexed { index, cidade ->
                                DropdownMenuItem(
                                    text = { Text(text = cidade) },
                                    onClick = {
                                        cidadeSelecionada = cidade
                                        isExpandedCidade = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .absolutePadding(right = 40.dp)
                )
            }
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val resultado: ApiResponse =
                                consultarAqi(cidadeSelecionada, estadoSelecionado)
                            println("Resultado da requisição: $resultado")
                            if (resultado != null && resultado.status == "success") {
                                navController.navigate("ResultScreen/${cidadeSelecionada}/${estadoSelecionado}/${resultado.data.current.pollution.aqius}")
                            } else {
                                println("Erro: Resposta da requisição nula ou falha.")
                            }
                        } catch (e: IOException) {
                            println("Erro ao fazer a requisição: ${e.message}")
                        }
                    }
                },
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(285.dp)
            ) {
                Text(text = "Ver Resultado")
            }
        }
    }
}

suspend fun consultarAqi(city: String, state: String): ApiResponse {
    return withContext(Dispatchers.IO) {
        try {
            val resultado = RetrofitClient.apiService.getCityData(city, state)
            resultado
        } catch (e: Exception) {
            throw IOException("Erro desconhecido ao fazer a requisição: ${e.message}")
        }
    }
}


object RetrofitClient {
    private const val BASE_URL = "http://api.airvisual.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
