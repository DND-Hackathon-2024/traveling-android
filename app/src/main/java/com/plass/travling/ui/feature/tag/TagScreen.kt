package com.plass.travling.ui.feature.tag

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.util.Log
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.plass.travling.MainActivity
import com.plass.travling.R
import com.plass.travling.remote.RetrofitBuilder
import com.plass.travling.remote.response.CouponResponse
import com.plass.travling.remote.response.PlaceResponse
import com.plass.travling.ui.component.ButtonState
import com.plass.travling.ui.component.Coupon
import com.plass.travling.ui.component.travelingVerticalGradient
import com.plass.travling.ui.feature.locate.RowShimmer
import com.plass.travling.ui.theme.TravelingColor
import com.plass.travling.ui.theme.TravelingTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TagScreen(
    navController: NavController,
    data: String,
    changeBottomNav: (visible: Boolean) -> Unit
) {
    val view = LocalView.current
    val coroutineScope = rememberCoroutineScope()
    var state by remember { mutableStateOf<CouponResponse?>(null) }
    var secondState by remember { mutableStateOf<PlaceResponse?>(null) }
    LifecycleResumeEffect(key1 = Unit) {
        val activity = (view.context as Activity)
        activity.window.statusBarColor = TravelingColor.Blue.toArgb()

        onPauseOrDispose {
            activity.window.statusBarColor = TravelingColor.White.toArgb()
        }
    }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                RetrofitBuilder.getCouponApi().couponById(data.replace("\u0000", "").toInt())
            }.onSuccess { firstData ->
                kotlin.runCatching {
                    RetrofitBuilder.getPlaceApi().getTrap(firstData.data.trap.first().id)
                }.onSuccess { secondData ->
                    coroutineScope.launch(Dispatchers.Main) {
                        state = firstData.data
                        secondState = secondData.data
                    }
                }.onFailure {
                    it.printStackTrace()
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    val context = LocalContext.current
    val activity = (context as Activity) as MainActivity
    val adapter = activity.getNfcAdapter()


    LifecycleStartEffect(key1 = Unit) {
        adapter.enableReaderMode(
            activity, NfcAdapter.ReaderCallback { tag: Tag? ->
            },
            NfcAdapter.FLAG_READER_NFC_A
                    or NfcAdapter.FLAG_READER_NFC_B
                    or NfcAdapter.FLAG_READER_NFC_F
                    or NfcAdapter.FLAG_READER_NFC_V
                    or NfcAdapter.FLAG_READER_NFC_BARCODE,
            null)
        onStopOrDispose {
            adapter.disableReaderMode(activity)
        }
    }

    DisposableEffect(key1 = navController) {

        onDispose {
            changeBottomNav(true)
        }
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .background(brush = travelingVerticalGradient())
    ) {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .background(brush = travelingVerticalGradient())
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 10.dp
                    )
                    .align(Alignment.Start),
                text = "태깅 성공",
                color = TravelingTheme.colorScheme.White,
                style = TravelingTheme.typography.headline1M
            )
            Spacer(modifier = Modifier.height(56.dp))
            if (state?.location == null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowShimmer(width = 100.dp)
                    RowShimmer(width = 200.dp)
                }
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${state?.location}에서\n새 트랩을 찾았어요!",
                    textAlign = TextAlign.Center,
                    color = TravelingTheme.colorScheme.White,
                    style = TravelingTheme.typography.title2B
                )
            }
            Spacer(modifier = Modifier.height(58.dp))
            Surface(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent
            ) {
                if (secondState == null) {
                    RowShimmer(width = 260.dp, height = 260.dp / 16f * 9f)
                } else {
                    Box {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(20.dp)),
                            model = secondState?.imgUrl,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                        var text = secondState?.address?: ""
                        text = if (text.length > 9) {
                            text.slice(0..<9) + "..."
                        } else text
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 13.dp
                                ),
                            text =  text,
                            color = TravelingTheme.colorScheme.White,
                            style = TravelingTheme.typography.headline2B
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (state?.trap != null) {
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
            }

            Spacer(modifier = Modifier.height(72.dp))
            if (state == null) {
                RowShimmer(width = 140.dp)
            } else {
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
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (state == null) {
                RowShimmer(width = 280.dp, height = 72.dp)
            } else {
                Coupon(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 22.dp),
                    title = "${state?.couponDiscount} 할인 쿠폰",
                    description = state?.description ?: "",
                    category = state?.location?: ""
                )
            }
            Spacer(modifier = Modifier.weight(1f))
//            Row(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    modifier = Modifier.size(24.dp),
//                    painter = painterResource(id = R.drawable.ic_instargram),
//                    contentDescription = ""
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "공유",
//                    color = TravelingTheme.colorScheme.White,
//                    style = TravelingTheme.typography.headline2B
//                )
//            }
//            Spacer(modifier = Modifier.height(14.dp))
            Spacer(modifier = Modifier.height(108.dp))
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            TagTVCTAButton(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 8.dp)
                    .height(54.dp),
                text = "확인",
                textColor = TravelingTheme.colorScheme.Black
            ) {
                navController.popBackStack()
            }
        }
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
                        painter = painterResource(id = it),
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
