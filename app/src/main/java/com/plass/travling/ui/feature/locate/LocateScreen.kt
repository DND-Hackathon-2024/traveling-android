package com.plass.travling.ui.feature.locate

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plass.travling.remote.RetrofitBuilder
import com.plass.travling.remote.response.PlaceResponse
import com.plass.travling.ui.component.travelingHorizontalGradient
import com.plass.travling.ui.theme.TravelingTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun LocateScreen(
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    var nowItem1 by remember { mutableStateOf(emptyList<PlaceResponse>()) }
    var nowItem2 by remember { mutableStateOf(emptyList<PlaceResponse>()) }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                RetrofitBuilder.getPlaceApi().getPlaceList()
            }.onSuccess {
                Log.d("TAG", "LocateScreen: ")
                val data = it.data
                nowItem1 = data.subList(0, 2)
                nowItem2 = data.subList(2, data.size-1)
            }.onFailure {

            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TravelingTheme.colorScheme.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "서울 강남 ",
                style = TravelingTheme.typography.title1B.copy(
                    brush = travelingHorizontalGradient()
                ),
            )
            Text(
                text = "주변",
                style = TravelingTheme.typography.title1B,
                color = TravelingTheme.colorScheme.Black
            )
        }
        LazyColumn {
            item {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                    text = "가장 가까이 있어요",
                    style = TravelingTheme.typography.headline2B,
                    color = TravelingTheme.colorScheme.Black
                )
            }
            items(nowItem1) {
                LocateItem(
                    locate = it.placeName,
                    image = it.imgUrl,
                    distance = Random.nextInt(1, 10),
                    like = Random.nextInt(1, 10),
                ) {

                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFF4F5F9)
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                    text = " 주변 NFC",
                    style = TravelingTheme.typography.headline2B,
                    color = TravelingTheme.colorScheme.Black
                )
            }
            items(nowItem2) {
                LocateItem(
                    locate = it.placeName,
                    image = it.imgUrl,
                    distance = Random.nextInt(1, 10),
                    like = Random.nextInt(1, 10),
                ) {

                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFF4F5F9)
                )
            }
        }

    }
}

data class LocateModel(
    val locate: String,
    val distance: Int,
    val like: Int,
    val image: String
)