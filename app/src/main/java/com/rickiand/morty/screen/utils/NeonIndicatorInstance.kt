package com.rickiand.morty.screen.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.rickiand.morty.ui.theme.Blue
import com.rickiand.morty.ui.theme.Orange
import com.rickiand.morty.ui.theme.Pink
import com.rickiand.morty.ui.theme.Purple
import com.rickiand.morty.ui.theme.Transparent
import com.rickiand.morty.ui.theme.Yellow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import java.lang.Math.abs
import kotlin.math.sign

class NeonIndicator(
    private val shape: Shape,
    private val borderWidth: Dp
) : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val instance = remember(interactionSource) {
            NeonIndicatorInstance(
                shape,
                // Double the border size for a stronger press effect
                borderWidth * 2
            )
        }

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> instance.animatedToPressed(interaction.pressPosition, this)
                    is PressInteraction.Release -> instance.animatedToResting(this)
                    is PressInteraction.Cancel -> instance.animatedToResting(this)
                }
            }
        }

        return instance
    }


    private class NeonIndicatorInstance(
        private val shape: Shape,
        private val borderWidth: Dp
    ) : IndicationInstance {

        var currentPressPosition: Offset = Offset.Zero
        val animatedProgress = Animatable(0f)
        val animatedPressAlpha = Animatable(1f)

        var pressedAnimation: Job? = null
        var restingAnimation: Job? = null

        fun animatedToPressed(pressPosition: Offset, scope: CoroutineScope) {
            val currentPressedAnimation = pressedAnimation
            pressedAnimation = scope.launch {
                restingAnimation?.cancelAndJoin()
                currentPressedAnimation?.cancelAndJoin()
                currentPressPosition = pressPosition
                animatedPressAlpha.snapTo(1f)
                animatedProgress.snapTo(0f)
                animatedProgress.animateTo(1f, tween(450))
            }
        }

        fun animatedToResting(scope: CoroutineScope) {
            restingAnimation = scope.launch {
                pressedAnimation?.join()
                animatedPressAlpha.animateTo(0f, tween(250))
                animatedProgress.snapTo(0f)
            }
        }

        override fun ContentDrawScope.drawIndication() {
            val (startPosition, endPosition) = calculateGradientStartAndEndFromPressPosition(
                currentPressPosition, size
            )
            val brush = animateBrush(
                startPosition = startPosition,
                endPosition = endPosition,
                progress = animatedProgress.value
            )
            val alpha = animatedPressAlpha.value

            drawContent()

            val outline = shape.createOutline(size, layoutDirection, this)
            // Draw overlay on top of content
            drawOutline(
                outline = outline,
                brush = brush,
                alpha = alpha * 0.1f
            )
            // Draw border on top of overlay
            drawOutline(
                outline = outline,
                brush = brush,
                alpha = alpha,
                style = Stroke(width = borderWidth.toPx())
            )
        }

        private fun animateBrush(
            startPosition: Offset,
            endPosition: Offset,
            progress: Float
        ): Brush {
            if (progress == 0f) return SolidColor(Color.Transparent)

            val colorsStop = buildList {
                when {
                    progress < 1 / 6f -> {
                        val adjustedProgress = progress * 6f
                        add(0f to Blue)
                        add(adjustedProgress to Transparent)
                    }

                    progress < 2 / 6f -> {
                        val adjustedProgress = (progress - 1 / 6f) * 6f
                        add(0f to Purple)
                        add(adjustedProgress * MaxBlueStop to Blue)
                        add(adjustedProgress to Blue)
                        add(1f to Transparent)
                    }

                    progress < 3 / 6f -> {
                        val adjustedProgress = (progress - 2 / 6f) * 6f
                        add(0f to Pink)
                        add(adjustedProgress * MaxPurpleStop to Purple)
                        add(MaxBlueStop to Blue)
                        add(1f to Blue)
                    }

                    progress < 4 / 6f -> {
                        val adjustedProgress = (progress - 3 / 6f) * 6f
                        add(0f to Orange)
                        add(adjustedProgress * MaxPinkStop to Pink)
                        add(MaxPurpleStop to Purple)
                        add(MaxBlueStop to Blue)
                        add(1f to Blue)
                    }

                    progress < 5 / 6f -> {
                        val adjustedProgress = (progress - 4 / 6f) * 6f
                        add(0f to Yellow)
                        add(adjustedProgress * MaxOrangeStop to Orange)
                        add(MaxPinkStop to Pink)
                        add(MaxPurpleStop to Purple)
                        add(MaxBlueStop to Blue)
                        add(1f to Blue)
                    }

                    else -> {
                        val adjustedProgress = (progress - 5 / 6f) * 6f
                        add(0f to Yellow)
                        add(adjustedProgress * MaxYellowStop to Yellow)
                        add(MaxOrangeStop to Orange)
                        add(MaxPinkStop to Pink)
                        add(MaxPurpleStop to Purple)
                        add(MaxBlueStop to Blue)
                        add(1f to Blue)
                    }
                }
            }
            return linearGradient(
                colorStops = colorsStop.toTypedArray(),
                start = startPosition,
                end = endPosition
            )
        }

        private fun calculateGradientStartAndEndFromPressPosition(
            pressPosition: Offset,
            size: Size
        ): Pair<Offset, Offset> {
            val offset = pressPosition - size.center
            val gradient = offset.y / offset.x
            val width = (size.width / 2f) * sign(offset.x)
            val height = (size.height / 2f) * sign(offset.y)
            val x = height / gradient
            val y = gradient * width

            val intercept = if (abs(y) <= abs(height)) {
                Offset(width, y)
            } else {
                Offset(x, height)
            }
            val start = intercept + size.center
            val end = Offset(size.width - start.x, size.height - start.y)
            return start to end
        }

        companion object {
            const val MaxYellowStop = 0.16f
            const val MaxOrangeStop = 0.33f
            const val MaxPinkStop = 0.5f
            const val MaxPurpleStop = 0.67f
            const val MaxBlueStop = 0.83f
        }

    }
}