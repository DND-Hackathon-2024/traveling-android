package com.plass.travling.ui.feature.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.plass.travling.R
import com.plass.travling.remote.RetrofitBuilder
import com.plass.travling.remote.response.CouponResponse
import com.plass.travling.ui.component.Coupon
import com.plass.travling.ui.component.GrowDialog
import com.plass.travling.ui.component.TVTextField
import com.plass.travling.ui.component.TVTopAppBar
import com.plass.travling.ui.component.bounceClick
import com.plass.travling.ui.component.shimmerEffect
import com.plass.travling.ui.feature.root.NavRoot
import com.plass.travling.ui.theme.TravelingTheme
import com.plass.travling.utiles.TAG
import com.plass.travling.utiles.showShortToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    changeBottomVisible: (Boolean) -> Unit
) {
    var location by remember {
        mutableStateOf("전체")
    }
    var isFetching by remember {
        mutableStateOf(false)
    }

    var coupons by remember { mutableStateOf(emptyList<CouponResponse>()) }
    val coroutineScope = rememberCoroutineScope()
    var tf by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    val clearState: () -> Unit = {
        coroutineScope.launch(Dispatchers.Main) {
            coupons = emptyList()
        }
    }

    var content by remember {
        mutableStateOf<String?>(null)
    }

    var showAI by remember {
        mutableStateOf(false)
    }
    if (showAI) {
        GrowDialog(
            title = "AI의 답변이에요",
            content = content
        ) {
            showAI = false
        }
    }

    fun handleAI() {
        coroutineScope.launch(Dispatchers.IO) {
            if (tf.isEmpty()) {
                return@launch
            }
            kotlin.runCatching {
                val t = RetrofitBuilder.getAI().get(tf)
                t
            }.onSuccess {
                coroutineScope.launch(Dispatchers.Main) {
                    tf = ""
                    content = it.massage
                    Log.d(TAG, "handleAI: ${it}")
                    showAI = true
                }
            }.onFailure {  }
        }
    }

    LaunchedEffect(key1 = location) {
        coroutineScope.launch(Dispatchers.IO) {
            clearState()
            if (location == "전체") {
                kotlin.runCatching {
                    coroutineScope.launch(Dispatchers.Main) {
                        isFetching = true
                    }
                    RetrofitBuilder.getCouponApi().couponAll()
                }.onSuccess {
                    coroutineScope.launch(Dispatchers.Main) {
                        isFetching = false
                        coupons = it.data
                    }
                }.onFailure {
                    Log.d(TAG, "HomeScreen: $it")
                    coroutineScope.launch(Dispatchers.Main) {
                        isFetching = false
                        context.showShortToast("불러오기에 실패하였습니다.")
                    }
                }
            } else {
                Log.d("TAG", "HomeScreen: called $location")
                kotlin.runCatching {
                    coroutineScope.launch(Dispatchers.Main) {
                        isFetching = true
                    }
                    RetrofitBuilder.getCouponApi().couponWithLocation(location)
                }.onSuccess {
                    coroutineScope.launch(Dispatchers.Main) {
                        coupons = it.data
                        isFetching = false
                    }
                }.onFailure {
                    coroutineScope.launch(Dispatchers.Main) {
                        context.showShortToast("불러오기에 실패하였습니다.")
                        isFetching = false
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        changeBottomVisible(true)
    }

    Column(
        modifier = Modifier
            .safeDrawingPadding()
            .background(Color(0xFFF4F5F9))
            .fillMaxSize()
    ) {
        TVTopAppBar(
            text = "홈",
            backgroundColor = Color(0xFF0078F9)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(start = 12.dp)
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "지금까지 총",
                            color = TravelingTheme.colorScheme.White,
                            style = TravelingTheme.typography.bodyRegular
                        )
                        Text(
                            text = "51 트랩",
                            color = TravelingTheme.colorScheme.White,
                            style = TravelingTheme.typography.title1B
                        )
                        Spacer(modifier = Modifier.height(11.dp))
                    }
                    Image(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(170.dp)
                            .padding(bottom = 11.dp, end = 0.dp),
                        painter = painterResource(id = R.drawable.ic_emoji),
                        contentDescription = ""
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TVTextField(
                modifier = Modifier
                    .weight(1f),
                hint = "AI에게 여행지에 관한 뭐든 물어보세요",
                value = tf,
                onValueChange = { tf = it }
            )
            Icon(
                modifier = Modifier
                    .bounceClick(onClick = {
                        handleAI()
                    })
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_search),
                tint = Color(0xFF989899),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.padding(
                start = 12.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
            text = "사용가능한 쿠폰이\n${coupons.size}개 남아있어요",
            color = TravelingTheme.colorScheme.Black,
            style = TravelingTheme.typography.headline2B
        )
        LazyRow(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(arrayListOf("전체", "대구", "포항", "경주", "청도", "마산", "김해")) {
                LocationCell(text = it, selected = it == location) {
                    location = it
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isFetching) {
                items(3) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth()
                            .height(72.dp)
                            .background(shimmerEffect(), RoundedCornerShape(4.dp))
                    )
                }
            } else {
                items(coupons) {
                    Coupon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .bounceClick {
                                navController.navigate(
                                    NavRoot.COUPON.replace("{id}", "${it.id}")
                                )
                            },
                        title = it.couponName,
                        description = it.description,
                        category = it.location,
                        isHome = true
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun LocationCell(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val w = if (selected) 1.5.dp else 1.dp
    val c = if (selected) TravelingTheme.colorScheme.Blue else Color(0xFFE3E3E3)
    val tc = if (selected) TravelingTheme.colorScheme.Blue else Color(0xFF646464)
    Box(
        modifier = Modifier
            .bounceClick(onClick = onClick)
            .border(width = w, color = c, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = TravelingTheme.typography.labelMedium,
            color = tc
        )
    }
}

data class CouponModel(
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String
)