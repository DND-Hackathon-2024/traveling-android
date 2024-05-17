package com.plass.travling

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plass.travling.ui.feature.nfc.NfcReadScreen
import com.plass.travling.ui.feature.nfc.NfcWriteScreen
import com.plass.travling.ui.feature.root.NavRoot
import com.plass.travling.ui.theme.TravlingTheme

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
            val navHostController = rememberNavController()
            TravlingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = NavRoot.MAIN
                    ) {
                        composable(NavRoot.MAIN) {
                            Column {
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
    TravlingTheme {
        Greeting("Android")
    }
}