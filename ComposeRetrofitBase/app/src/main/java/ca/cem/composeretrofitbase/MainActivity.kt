package ca.cem.composeretrofitbase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.cem.composeretrofitbase.api.RetrofitInstance
import ca.cem.composeretrofitbase.ui.theme.ComposeRetrofitBaseTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeRetrofitBaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EcranDoubler(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EcranDoubler(modifier: Modifier = Modifier) {
    var nombreSaisi by remember { mutableStateOf("") }
    var resultat by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = nombreSaisi,
            onValueChange = { nombreSaisi = it },
            label = { Text("Entrez un nombre") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val nombre = nombreSaisi.toDoubleOrNull()?.toLong()
                if (nombre == null) return@Button
                Log.d("EcranDoubler", "Appel API avec nombre=$nombre")
                RetrofitInstance.api.doubler(nombre).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            resultat = response.body() ?: "Aucun résultat"
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("EcranDoubler", "Entrée dans la fonction onFailure : ${t.message}")
                        Log.e("EcranDoubler", "Erreur lors de la récupération du résultat", t)
                        Log.d("EcranDoubler", "Sortie de la fonction onFailure")
                    }
                })
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Doubler")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (resultat.isNotEmpty()) {
            Text(
                text = resultat,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcranDoublerPreview() {
    ComposeRetrofitBaseTheme {
        EcranDoubler()
    }
}