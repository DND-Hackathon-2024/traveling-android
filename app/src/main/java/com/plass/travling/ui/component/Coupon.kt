package com.plass.travling.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plass.travling.R
import com.plass.travling.ui.theme.TravelingTheme

@Composable
fun Coupon(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    category: String,
) {
    Row(
        modifier = modifier
            .height(75.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = TravelingTheme.colorScheme.White,
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        bottomStart = 4.dp
                    )
                )
                .padding(
                    start = 10.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
            ) {
                Text(
                    text = title,
                    color = TravelingTheme.colorScheme.Black,
                    style = TravelingTheme.typography.bodyBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    color = TravelingTheme.colorScheme.Gray7E,
                    style = TravelingTheme.typography.captionMedium
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Category(text = category)
            Spacer(modifier = Modifier.width(10.dp))
        }
        Box(
            modifier = Modifier
                .width(10.dp)
                .fillMaxHeight()
                .background(
                    color = Color(0xFFF97D6D),
                    shape = RoundedCornerShape(
                        topEnd = 4.dp,
                        bottomEnd = 4.dp
                    )
                )
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_coupon_polygon),
                contentDescription = ""
            )
        }
    }
}