package com.plass.travling.ui.feature.coupon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.plass.travling.R
import com.plass.travling.ui.component.Category
import com.plass.travling.ui.component.bounceClick
import com.plass.travling.ui.theme.TravelingTheme

@Composable
fun CouponScreen(
    navController: NavController,
    id: Int
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.height(48.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    modifier = Modifier.bounceClick {
                        navController.popBackStack()
                    },
                    painter = painterResource(id = R.drawable.ic_lefts),
                    contentDescription = "뒤로가기"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "쿠폰",
                    color = TravelingTheme.colorScheme.Black,
                    style = TravelingTheme.typography.headline1M
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    model = "https://mblogthumb-phinf.pstatic.net/MjAyMzAyMDFfMTEz/MDAxNjc1MjQ2NjI5MTI5.rqbiSaXfZoGVSNIT8VJumWFyyShaHlPzqvNIQ15_ILkg.a0ABYCO3NtT-K9nIa_7xlTkf2uya9vZ_0_V-kDKWRKEg.JPEG.hans9090/SE-1ed8394f-3b4f-4331-ad97-baa590dde265.jpg?type=w800",
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            start = 12.dp,
                            bottom = 20.dp
                        ),
                    text = "대구광역시동부도서관",
                    color = TravelingTheme.colorScheme.White,
                    style = TravelingTheme.typography.headline2B
                )
            }

            Column(
                modifier = Modifier
                    .offset(y = (-8).dp)
                    .background(
                        color = TravelingTheme.colorScheme.White,
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                        )
                    )
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                Category(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "대구"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "40% 할인 쿠폰",
                    color = TravelingTheme.colorScheme.Black,
                    style = TravelingTheme.typography.headline2B
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp
                        )
                        .align(Alignment.CenterHorizontally)
                        .aspectRatio(16f/9f),
                    painter = painterResource(id = R.drawable.ic_barcode),
                    contentDescription = "바코드",
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(
                            horizontal = 24.dp
                        )
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color(0xFFDDDDDD)
                            ),
                            shape = RoundedCornerShape(4.dp)

                        )
                        .bounceClick { }
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = "download",
                            colorFilter = ColorFilter.tint(Color(0xFF818181))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "쿠폰 저장",
                            color = Color(0xFF818181),
                            style = TravelingTheme.typography.headline2B
                        )
                    }
                }
            }
        }
    }
}