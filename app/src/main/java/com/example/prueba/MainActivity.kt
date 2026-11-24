package com.example.prueba

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.prueba.ui.theme.PruebaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }

        lifecycleScope.launch {
            delay(3000)
            keepSplash = false
        }

        super.onCreate(savedInstanceState)

        ShowToast("Funcion onCreate()")
        enableEdgeToEdge()
        setContent {
            PruebaTheme {
                ViewContainer()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ShowToast("Funcion onStart()")
    }

    fun ShowToast(message:String){
        val toast = Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        )
        toast.show()
        Log.i(TAG, message)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    //locale = "ar-rEG",
    //device = "spec:width=411dp,height=891dp,orientation=landscape"
    )

@Composable
fun ViewContainer(){

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { /* Drawer content */ }
        },
    ) {
        Scaffold(

            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.home_screen)) },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme
                            .secondary,
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.back_btn_desc),
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { openWebScreen(context) }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Marcar como favorito",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { openSecondActivity(context) }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = Color.White
                            )
                        }
                        DropDownMenuOptions()
                    }
                )
            },

            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = stringResource(R.string.bottom_app_bar),
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },

            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "\uD83D\uDE2D\uD83D\uDE2Dأشعر بالنعاس\uD83D\uDE2D\uD83D\uDE2D",
                            actionLabel = "مرحبًا",
                            duration = SnackbarDuration.Long
                        )
                    }
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.home_fab_text)
                    )
                }
            },

            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }

        ) { innerPadding ->
            ContentView(modifier = Modifier.padding(innerPadding))
        }
    }
}

fun openWebScreen(context: Context) {
    val url = Uri.parse("https://aguascalientes.tecnm.mx/")
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}

fun openSecondActivity(context: Context) {

    // Creating explicit intent to go to SecondActivity
    val intent = Intent(context, SecondActivity::class.java)
    context.startActivity(intent) // Launch the activity
}

fun openWhatsApp(context: Context) {

    val number = "+00 9876543210"
    val url = "https://api.whatsapp.com/send?phone=" + number
    val i = Intent(Intent.ACTION_VIEW)
    i.setData(Uri.parse(url))
    context.startActivity(i)

    /*val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com"))
        )
    }*/
}

fun openTwitter(context: Context) { // MODIFICAR

    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=FranYaoi"))
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"))

    try {
        context.startActivity(appIntent)
    } catch (e: Exception) {
        context.startActivity(webIntent)
    }

    /*val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"))
        )
    }*/
}

fun openCamera(context: Context) {

    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se encontró ninguna aplicación de cámara", Toast.LENGTH_SHORT).show()
    }

    /*val intent = Intent(Intent.ACTION_VIEW).apply {
        setPackage("com.google.android.apps.cam")
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No hay app de Camara instalada", Toast.LENGTH_SHORT).show()
    }*/
}

@Composable
fun DropDownMenuOptions() {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.options_menu),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.profile)) },
                onClick = {
                    expanded = false
                    // Acción para Perfil
                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.config)) },
                onClick = {
                    expanded = false
                    // Acción para Configuración
                },
                leadingIcon = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.whatsapp)) },
                onClick = {
                    expanded = false
                    openWhatsApp(context)
                },
                leadingIcon = {
                    Icon(Icons.Default.Sms, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.twitter)) },
                onClick = {
                    expanded = false
                    openTwitter(context)
                },
                leadingIcon = {
                    Icon(Icons.Default.Apps, contentDescription = null)
                }
            )

            DropdownMenuItem(
                text = { Text(stringResource(R.string.camara)) },
                onClick = {
                    expanded = false
                    openCamera(context)
                },
                leadingIcon = {
                    Icon(Icons.Default.Camera, contentDescription = null)
                }
            )

            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(stringResource(R.string.logout)) },
                onClick = {
                    expanded = false
                    // Acción para Cerrar sesión
                },
                leadingIcon = {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                }
            )
        }
    }
}



@Composable
fun ContentView(modifier: Modifier = Modifier){
    var counter by rememberSaveable { mutableStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(8.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.gato),
                contentDescription = "Imagen gato",
                modifier = Modifier
                    .fillMaxSize()
                    .height(400.dp)
            )

            Row(modifier = Modifier.padding(top = 2.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.outline_adb_24),
                    contentDescription = "Android",
                    modifier = Modifier
                        .clickable { counter++ }
                        .padding(start = 12.dp)
                )
                Text(
                    text = counter.toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Text(
                text = stringResource(R.string.materia),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.nombre),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.apellido),
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.gatoxd),
                        contentDescription = "Gato xd",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gatoxd_2),
                        contentDescription = "Gato xd",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gatoxd),
                        contentDescription = "Gato xd",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gatoxd_2),
                        contentDescription = "Gato xd",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.gatoxd_3),
                        contentDescription = "Gato xd",
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}