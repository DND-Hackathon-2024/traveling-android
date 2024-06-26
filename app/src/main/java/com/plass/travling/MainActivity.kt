package com.plass.travling

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plass.travling.ui.feature.home.HomeScreen
import androidx.navigation.navArgument
import com.plass.travling.local.SharedPreferencesManager
import com.plass.travling.ui.feature.coupon.CouponScreen
import com.plass.travling.ui.feature.nfc.NfcTagDialog
import com.plass.travling.ui.feature.locate.LocateScreen
import com.plass.travling.ui.feature.join.JoinScreen
import com.plass.travling.ui.feature.locate.LocateItem
import com.plass.travling.ui.feature.locate.LocateScreen
import com.plass.travling.ui.feature.login.LoginScreen
import com.plass.travling.ui.feature.nfc.NfcReadScreen
import com.plass.travling.ui.feature.nfc.NfcTagDialog
import com.plass.travling.ui.feature.nfc.NfcWriteScreen
import com.plass.travling.ui.feature.root.BottomNavItem
import com.plass.travling.ui.feature.root.BottomNavigation
import com.plass.travling.ui.feature.root.NavRoot
import com.plass.travling.ui.feature.tag.TagScreen
import com.plass.travling.ui.theme.TravelingColor
import com.plass.travling.ui.theme.TravelingTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pending : PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val launchIntent = Intent(this, this.javaClass)
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pending = PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE)


        setContent {
            val coroutineScope = rememberCoroutineScope()
            val navHostController = rememberNavController()
            var navItems by remember { mutableStateOf(listOf(
                BottomNavItem(NavRoot.HOME, true, R.drawable.ic_home),
                BottomNavItem("nfc", false, R.drawable.ic_nfc),
                BottomNavItem(NavRoot.LOCATE, false, R.drawable.ic_locate),
            )) }

            var isShowBottomNavigationBar by remember { mutableStateOf(true) }

            val changeBottomNav: (visible: Boolean) -> Unit = {
                coroutineScope.launch {
                    isShowBottomNavigationBar = it
                }
            }
            
            var isShowNfcDialog by remember { mutableStateOf(false) }

            TravelingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isShowNfcDialog) {
                        NfcTagDialog(
                            title = "태깅 준비 완료",
                            description = "NFC를 휴대폰 뒤쪽에 태깅해주세요",
                            onClickCancel = {
                                coroutineScope.launch {
                                    isShowNfcDialog = false
                                }
                            },
                            onSuccess = {
                                coroutineScope.launch {
                                    isShowNfcDialog = false
                                    changeBottomNav(false)
                                    navHostController.navigate(
                                        NavRoot.TAGGING.replace("{data}", it)
                                    )
                                }
                            }
                        )
                    }
                    
                    Scaffold(
                        bottomBar = {
                            if (isShowBottomNavigationBar) {
                                BottomNavigation(
                                    modifier = Modifier,
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
                                        coroutineScope.launch {
                                            isShowNfcDialog = true
                                        }
                                    }
                                )
                            }
                        }
                    ) {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navHostController,
                            startDestination = getStartDestination()
                        ) {

                            composable(NavRoot.NFC_WRITE) {
                                NfcWriteScreen(navController = navHostController)
                            }

                            composable(NavRoot.NFC_READ) {
                                NfcReadScreen(navController = navHostController)
                            }
                            composable(NavRoot.LOCATE) {
                                LocateScreen(navController = navHostController)
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
                            composable(NavRoot.HOME) {
                                HomeScreen(
                                    navController = navHostController,
                                    changeBottomVisible = changeBottomNav
                                )
                            }

                            composable(
                                route = NavRoot.TAGGING,
                                arguments = listOf(
                                    navArgument("data") { type = NavType.StringType }
                                )
                            ) {
                                TagScreen(
                                    navController = navHostController,
                                    data = it.arguments?.getString("data")!!,
                                    changeBottomNav = changeBottomNav,
                                )
                            }

                            composable(
                                route = NavRoot.COUPON,
                                arguments = listOf(
                                    navArgument("id") { type = NavType.IntType}
                                )
                            ) {
                                CouponScreen(
                                    navController = navHostController,
                                    id = it.arguments?.getInt("id")!!,
                                    changeBottomNav = changeBottomNav
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        this.window.statusBarColor = TravelingColor.Blue.toArgb()
    }

    private fun getStartDestination(): String {
        val token = SharedPreferencesManager.get("token") ?: ""
        return if (token.isEmpty()) NavRoot.LOGIN else NavRoot.HOME
    }



    fun getNfcAdapter(): NfcAdapter = nfcAdapter
}
