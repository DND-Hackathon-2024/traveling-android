package com.plass.travling.ui.component

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class DropShadowType(
    val blur: Dp,
    val offsetY: Dp,
    val color: Color,
) {
    data object Nav : DropShadowType(4.dp, 4.dp, Color(0x40000000))
}
/**
 * Seugi DropShadow
 *
 * @param type: the size of the shadow according to the design system.
 * @param modifier: the Modifier to be applied to this drop shadow
 */
fun Modifier.dropShadow(type: DropShadowType, modifier: Modifier = Modifier) = then(
    modifier.drawBehind {
        drawIntoCanvas { canvas ->
            type.run {
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()
                val spreadPixel = 0f
                val leftPixel = (0f - spreadPixel) + 0f // offsetX
                val topPixel = (0f - spreadPixel) + type.offsetY.toPx()
                val rightPixel = size.width + spreadPixel
                val bottomPixel = size.height + spreadPixel

                frameworkPaint.color = type.color.toArgb()

                if (type.blur != 0.dp) {
                    frameworkPaint.maskFilter = BlurMaskFilter(
                        type.blur.toPx(),
                        BlurMaskFilter.Blur.NORMAL,
                    )
                }

                canvas.drawRoundRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    radiusX = 0f,
                    radiusY = 0f,
                    paint = paint,
                )
            }
        }
    },
)