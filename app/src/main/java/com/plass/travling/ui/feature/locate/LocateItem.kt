package com.plass.travling.ui.feature.locate

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plass.travling.R
import com.plass.travling.ui.component.rippleClickable
import com.plass.travling.ui.theme.TravelingTheme

@Composable
fun LocateItem(
    locate: String,
    @DrawableRes resId: Int,
    distance: Int,
    like: Int,
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .rippleClickable(onClick = onClickItem)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = painterResource(id = resId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = locate,
                        style = TravelingTheme.typography.bodyBold,
                        color = TravelingTheme.colorScheme.Black
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = distance.toString(),
                        style = TravelingTheme.typography.labelRegular,
                        color = TravelingTheme.colorScheme.Gray6
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_locate),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(TravelingTheme.colorScheme.Gray98)
                )
                Text(
                    text = like.toString(),
                    style = TravelingTheme.typography.labelRegular,
                    color = TravelingTheme.colorScheme.Gray98
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}