package com.plass.travling.ui.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plass.travling.local.SharedPreferencesManager
import com.plass.travling.remote.RetrofitBuilder
import com.plass.travling.remote.request.LoginRequest
import com.plass.travling.ui.component.TVCTAButton
import com.plass.travling.ui.component.TVTextField
import com.plass.travling.ui.component.TVTopAppBar
import com.plass.travling.ui.feature.root.NavRoot
import com.plass.travling.ui.theme.TravelingTheme
import com.plass.travling.utiles.showShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    hideBottomNav: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        hideBottomNav()
    }

    var id by remember {
        mutableStateOf("")
    }
    var pw by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()

    var insertText by remember { mutableStateOf("") }
    val sendMessage: (String) -> Unit = {
        coroutineScope.launch {
            context.showShortToast(it)
        }
    }


    TVTopAppBar(
        text = "로그인",
        backgroundColor = TravelingTheme.colorScheme.White,
        textColor = TravelingTheme.colorScheme.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "아이디",
                style = TravelingTheme.typography.bodyBold
            )
            TVTextField(value = id, onValueChange = { id = it })
            Text(
                text = "비밀번호",
                style = TravelingTheme.typography.bodyBold
            )
            TVTextField(secured = true, value = pw, onValueChange = { pw = it })
            Spacer(modifier = Modifier.weight(1f))
            TVCTAButton(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = "로그인"
            ) {
                if (id.isEmpty()) {
                    sendMessage("아이디가 입력되지 않았습니다.")
                    return@TVCTAButton
                }
                if (pw.isEmpty()) {
                    sendMessage("비밀번호가 입력되지 않았습니다.")
                    return@TVCTAButton
                }
                coroutineScope.launch(Dispatchers.IO) {
                    val token = RetrofitBuilder.getMemberApi().login(
                        LoginRequest(
                            phone = id,
                            password = pw
                        )
                    ).data
                    SharedPreferencesManager.set("token", token)
                    coroutineScope.launch(Dispatchers.Main) {
                        navController.navigate(NavRoot.HOME)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {

    LoginScreen(navController = rememberNavController()) {

    }

}