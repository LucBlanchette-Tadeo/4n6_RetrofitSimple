package ca.cem.composeretrofitbase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.cem.composeretrofitbase.api.RetrofitInstance
import ca.cem.composeretrofitbase.api.Truc
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
    var listeLongs by remember { mutableStateOf<List<Double>>(emptyList()) }
    var listeTrucs by remember { mutableStateOf<List<Truc>>(emptyList()) }

    LaunchedEffect(Unit) {
        RetrofitInstance.api.longList().enqueue(object : Callback<List<Double>> {
            override fun onResponse(call: Call<List<Double>>, response: Response<List<Double>>) {
                if (response.isSuccessful) {
                    listeLongs = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Double>>, t: Throwable) {
                Log.d("EcranDoubler", "Entrée dans la fonction onFailure : ${t.message}")
                Log.e("EcranDoubler", "Erreur lors de la récupération de la liste de Long", t)
                Log.d("EcranDoubler", "Sortie de la fonction onFailure")
            }
        })

        RetrofitInstance.api.trucList().enqueue(object : Callback<List<Truc>> {
            override fun onResponse(call: Call<List<Truc>>, response: Response<List<Truc>>) {
                if (response.isSuccessful) {
                    listeTrucs = response.body() ?: emptyList()
                }
            }

            override fun onFailure(call: Call<List<Truc>>, t: Throwable) {
                Log.d("EcranDoubler", "Entrée dans la fonction onFailure : ${t.message}")
                Log.e("EcranDoubler", "Erreur lors de la récupération de la liste de Truc", t)
                Log.d("EcranDoubler", "Sortie de la fonction onFailure")
            }
        })
    }

    Row(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            items(listeLongs) { nombre ->
                Text(
                    text = nombre.toLong().toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            items(listeTrucs) { truc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "a : ${truc.a}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "b : ${truc.b}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "taille de c : ${truc.c.size}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
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

