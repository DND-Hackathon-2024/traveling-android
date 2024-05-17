package com.plass.travling.ui.feature.tag

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.plass.travling.R
import com.plass.travling.ui.component.ButtonState
import com.plass.travling.ui.component.Coupon
import com.plass.travling.ui.component.DropShadowType
import com.plass.travling.ui.component.TVCTAButton
import com.plass.travling.ui.component.dropShadow
import com.plass.travling.ui.component.travelingVerticalGradient
import com.plass.travling.ui.theme.TravelingColor
import com.plass.travling.ui.theme.TravelingTheme

@Composable
fun TagScreen(
    navController: NavController,
    data: String,
    changeBottomNav: (visible: Boolean) -> Unit
) {
    val view = LocalView.current
    LifecycleResumeEffect(key1 = Unit) {
        val activity = (view.context as Activity)
        activity.window.statusBarColor = TravelingColor.Blue.toArgb()

        onPauseOrDispose {
            activity.window.statusBarColor = TravelingColor.White.toArgb()
        }
    }

    DisposableEffect(key1 = navController) {

        onDispose {
            changeBottomNav(true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = travelingVerticalGradient()
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            ),
            text = "태깅 성공",
            color = TravelingTheme.colorScheme.White,
            style = TravelingTheme.typography.headline1M
        )
        Spacer(modifier = Modifier.height(56.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${data}에서\n새 트랩을 찾았어요!",
            textAlign = TextAlign.Center,
            color = TravelingTheme.colorScheme.White,
            style = TravelingTheme.typography.title2B
        )
        Spacer(modifier = Modifier.height(58.dp))
        Surface(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
        ) {
            Box {
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                    model = "https://mblogthumb-phinf.pstatic.net/MjAyMzAyMDFfMTEz/MDAxNjc1MjQ2NjI5MTI5.rqbiSaXfZoGVSNIT8VJumWFyyShaHlPzqvNIQ15_ILkg.a0ABYCO3NtT-K9nIa_7xlTkf2uya9vZ_0_V-kDKWRKEg.JPEG.hans9090/SE-1ed8394f-3b4f-4331-ad97-baa590dde265.jpg?type=w800",
                    contentDescription = "",
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 13.dp
                        ),
                    text = "대구 z존\n역사와 전통의 도서관",
                    color = TravelingTheme.colorScheme.White,
                    style = TravelingTheme.typography.headline2B
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_polygon),
            contentDescription = ""
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(
                    color = TravelingTheme.colorScheme.White,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                text = "찾지 못한 트랩이 1개 더 있어요!",
                color = TravelingTheme.colorScheme.Black,
                style = TravelingTheme.typography.labelMedium
            )
        }


        Spacer(modifier = Modifier.height(98.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            text = "1개의 쿠폰을 발견했어요",
            color = TravelingTheme.colorScheme.White,
            style = TravelingTheme.typography.headline1B
        )
        Spacer(modifier = Modifier.height(8.dp))
        Coupon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 22.dp),
            title = "40% 할인 쿠폰",
            description = "\"병준이와 단둘이 데이트권\"과 교환 가능",
            category = "대구"
        )

        Spacer(modifier = Modifier.height(74.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_instargram),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "공유",
                color = TravelingTheme.colorScheme.White,
                style = TravelingTheme.typography.headline2B
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        TagTVCTAButton(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(54.dp),
            text = "확인",
            textColor = TravelingTheme.colorScheme.Black
        ) {
            navController.popBackStack()
        }
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    TagScreen(navController = rememberNavController(), data = "", {})
}



@Composable
private fun TagTVCTAButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    textColor: Color = TravelingTheme.colorScheme.White,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    val isEnabled = enabled && !isLoading

    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val color = TravelingTheme.colorScheme.White
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Idle) 1f else 0.96f,
        label = "",
    )
    val animColor by animateColorAsState(
        targetValue = if (buttonState == ButtonState.Idle) {
            color
        } else {
            color
        },
        label = "",
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(buttonState) {
                if (!isEnabled) return@pointerInput
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Hold) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        ButtonState.Hold
                    }
                }
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = animColor,
//            contentColor = TravelingTheme.colorScheme.textNormal,
            disabledContainerColor = animColor,
//            disabledContentColor = TravelingTheme.colorScheme.buttonTextDisabled,
        ),
        enabled = isEnabled,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(0.dp),
        interactionSource = interactionSource,
    ) {
//            if (isLoading) {
//                RiveAnimation(
//                    resId = R.raw.loading_dots,
//                    contentDescription = "loading gif",
//                    autoplay = true,
//                    animationName = type.animName,
//                )
//            } else {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = TravelingTheme.colorScheme.Black,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TravelingTheme.colorScheme.Black
                leftIcon?.let {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        painter =  painterResource(id = it),
                        tint = textColor,
                        contentDescription = null
                    )
                }
                Text(
                    text = text,
                    style = TravelingTheme.typography.bodyBold,
                    color = textColor
                )
            }
        }
    }
}
