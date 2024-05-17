package com.plass.travling.ui.feature.join

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plass.travling.ui.component.TVCTAButton
import com.plass.travling.ui.component.TVTextField
import com.plass.travling.ui.component.TVTopAppBar
import com.plass.travling.ui.feature.login.LoginScreen
import com.plass.travling.ui.theme.TravelingTheme

@Composable
fun JoinScreen(
    navController: NavController,
    hideBottomNav: () -> Unit
) {

    LaunchedEffect(Unit) {
        hideBottomNav()
    }

    var id by remember {
        mutableStateOf("")
    }
    var pw by remember {
        mutableStateOf("")
    }
    TVTopAppBar(text = "회원가입") {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                text = "회원가입"
            ) {

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