package com.example.climaapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.climaapp.repository.WeatherRepository
import com.example.climaapp.ui.theme.ClimaAppTheme
import com.example.climaapp.viewmodel.WheatherviewModel
import com.example.climaapp.viewmodel.WheatherviewModelFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita que el contenido ocupe toda la pantalla, incluso en áreas como la barra de estado
        enableEdgeToEdge()

        // Establece el contenido de la actividad con Compose
        setContent {
            // Aplicar tema personalizado
            ClimaAppTheme {
                // Scaffold crea la estructura básica para la UI con soporte para barras, fondos, etc.
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // Llamamos a nuestra pantalla principal que muestra el clima
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen() {
    // Creamos el repositorio para acceder a la API y el factory para crear el ViewModel
    val repository = remember { WeatherRepository() }
    val factory = remember { WheatherviewModelFactory(repository) }

    // Obtenemos el ViewModel usando el factory para manejar el estado y lógica
    val viewModel: WheatherviewModel = viewModel(factory = factory)

    // Recogemos los estados que expone el ViewModel para reaccionar en UI
    val weatherState by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Carga el clima para la ciudad Bogotá solo una vez cuando se crea esta Composable
    LaunchedEffect(Unit) {
        viewModel.getWeather("Bogotá")
    }

    // Usamos Box para superponer la imagen de fondo y el contenido encima
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo que cubre toda la pantalla
        Image(
            painter = painterResource(id = R.drawable.background_weather),
            contentDescription = "Fondo clima",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Contenedor vertical centrado para el contenido visible sobre la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                // Mostrar indicador de carga mientras se obtiene el clima
                isLoading -> {
                    CircularProgressIndicator()
                }
                // Mostrar mensaje de error si hubo algún problema al obtener el clima
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                // Mostrar datos del clima si se obtuvieron correctamente
                weatherState != null -> {
                    Text(
                        text = weatherState!!.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black // Texto oscuro para mejor visibilidad sobre fondo claro
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${weatherState!!.main.temp}°C",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        // Ponemos la descripción del clima con mayúscula inicial
                        text = weatherState!!.weather[0].description.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
                // Estado inicial o si no hay datos, mostramos mensaje informativo
                else -> {
                    Text(
                        "No hay datos del clima aún.",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}
