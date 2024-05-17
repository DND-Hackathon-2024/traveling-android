package com.plass.travling

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plass.travling.ui.feature.join.JoinScreen
import com.plass.travling.ui.feature.locate.LocateItem
import com.plass.travling.ui.feature.login.LoginScreen
import com.plass.travling.ui.feature.nfc.NfcReadScreen
import com.plass.travling.ui.feature.nfc.NfcWriteScreen
import com.plass.travling.ui.feature.root.BottomNavItem
import com.plass.travling.ui.feature.root.BottomNavigation
import com.plass.travling.ui.feature.root.NavRoot
import com.plass.travling.ui.theme.TravelingTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pending : PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val launchIntent = Intent(this, this.javaClass)
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pending = PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE)

        setContent {
            val coroutineScope = rememberCoroutineScope()
            val navHostController = rememberNavController()
            var navItems by remember { mutableStateOf(listOf(
                BottomNavItem(NavRoot.MAIN, true, R.drawable.ic_home),
                BottomNavItem("nfc", false, R.drawable.ic_nfc),
                BottomNavItem(NavRoot.NFC_WRITE, false, R.drawable.ic_locate),
            )) }

            var isShowBottomNavigationBar by remember { mutableStateOf(true) }

            val changeBottomNav: (visible: Boolean) -> Unit = {
                coroutineScope.launch {
                    isShowBottomNavigationBar = it
                }
            }

            TravelingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (isShowBottomNavigationBar) {
                                BottomNavigation(
                                    items = navItems,
                                    onClickItem = { item ->
                                        val items = navItems.map {
                                            it.copy(
                                                isSelected = it.title == item.title
                                            )
                                        }
                                        coroutineScope.launch {
                                            navHostController.navigate(item.title)
                                            navItems = items
                                        }
                                    },
                                    onClickNfc = {

                                    }
                                )
                            }
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navHostController,
                            startDestination = NavRoot.MAIN
                        ) {
                            composable(NavRoot.MAIN) {
                                Column {
                                    LocateItem(locate = "test", resId = R.drawable.ic_profile, distance = 5, like = 3) {

                                    }
                                    Button(onClick = { navHostController.navigate(NavRoot.NFC_WRITE) }) {
                                        Text(text = "쓰기")
                                    }

                                    Button(onClick = { navHostController.navigate(NavRoot.NFC_READ) }) {
                                        Text(text = "읽기")
                                    }
                                }
                            }

                            composable(NavRoot.NFC_WRITE) {
                                NfcWriteScreen(navController = navHostController)
                            }

                            composable(NavRoot.NFC_READ) {
                                NfcReadScreen(navController = navHostController)
                            }
                            composable(NavRoot.LOGIN) {
                                LoginScreen(navController = navHostController) {
                                    changeBottomNav(false)
                                }
                            }
                            composable(NavRoot.JOIN) {
                                JoinScreen(navController = navHostController) {
                                    changeBottomNav(false)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        nfcAdapter.enableReaderMode(this, NfcAdapter.ReaderCallback { tag: Tag? ->
//            val testData = "https://byungjuns.xyz"
//            val message = NdefMessage(NdefRecord.createUri(testData))
//            val size = message.toByteArray().size
//
//            try {
//                val ndef = Ndef.get(tag)
//                if (ndef != null) {
//                    ndef.connect()
//                    if (!ndef.isWritable) {
//                        Log.e("enter", "cannot write")
//                    }
//                    if (ndef.maxSize < size) {
//                        Log.e("enter", "cannot size exception")
//                    }
//                    ndef.writeNdefMessage(message)
//                    Log.d("TAG", "onStart: 성공")
////                    vm.nfcSuccess()
//                }
//            } catch (e: Exception) {
//                Log.i("writeError", e.message.toString());
//            }},
//            NfcAdapter.FLAG_READER_NFC_A
//                    or NfcAdapter.FLAG_READER_NFC_B
//                    or NfcAdapter.FLAG_READER_NFC_F
//                    or NfcAdapter.FLAG_READER_NFC_V
//                    or NfcAdapter.FLAG_READER_NFC_BARCODE,
//            null)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        nfcAdapter.disableReaderMode(this);
//    }

    fun getNfcAdapter(): NfcAdapter = nfcAdapter
    fun getPending(): PendingIntent = pending
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TravelingTheme {
        Greeting("Android")
    }
}