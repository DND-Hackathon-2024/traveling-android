package com.plass.travling.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
fun TVTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = TravelingTheme.colorScheme.Blue,
    onClickBackButton: (() -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(
                    bottomStart = 18.dp,
                    bottomEnd = 18.dp
                )
            )
            .safeDrawingPadding()
    ) {
        Row(
            modifier = modifier
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                onClickBackButton?.let {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(2.dp),
                        painter = painterResource(id = R.drawable.ic_expand_left),
                        tint = TravelingTheme.colorScheme.Black,
                        contentDescription = null
                    )
                }
                val textStyle = TravelingTheme.typography.title1B
                Text(
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            top = 12.dp
                        ),
                    text = text,
                    style = textStyle,
                    color = TravelingTheme.colorScheme.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
            ) {
                trailingContent?.let { content ->
                    content()
                }
            }
        }
        content()
    }
}
