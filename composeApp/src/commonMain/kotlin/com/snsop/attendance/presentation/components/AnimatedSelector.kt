package com.snsop.attendance.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.snsop.attendance.domain.model.enums.Criteria
import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.Dimen
import com.snsop.attendance.utils.bounceClick
import kotlin.math.roundToInt


@Composable
@Preview(showBackground = true)
fun AnimatedSelector(
    modifier: Modifier = Modifier,
    name: String = "Name",
    description: String = "Description",
    isPositive: Boolean = true,
    onToggle: (Boolean) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(vertical = Dimen.Medium),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        BoxWithConstraints(
            modifier = Modifier
                .height(Dimen.ExtraLarge)
                .weight(1.6f)
                .clip(MaterialTheme.shapes.medium)
                .bounceClick {
                    onToggle(!isPositive)
                }
        ) {
            // Animated background indicator
            val offsetX by animateFloatAsState(
                targetValue = if (isPositive) 1f else 0f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                label = "background_slide"
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .offset {
                        IntOffset(
                            x = (offsetX * (this@BoxWithConstraints.constraints.maxWidth / 2)).roundToInt(),
                            y = 0
                        )
                    }
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            )

            // Text options
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            ) {
                val position = if (isPositive) 1 else 0
                listOf("No", "Yes").forEachIndexed { index, item ->
                    val animatedColor by animateColorAsState(
                        targetValue = if (position == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        animationSpec = tween(durationMillis = 300)
                    )
                    Text(
                        text = item, // Replace with "Present" or similar
                        modifier = Modifier
                            .weight(1f),
                        style = MaterialTheme.typography.labelLarge,
                        color = animatedColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedSelector(
    name: String,
    items: List<String>,
    position: Int,
    onToggle: (position: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.Tiny)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = Dimen.SmallPlus, horizontal = Dimen.Small)
        )

        BoxWithConstraints(
            modifier = Modifier
                .height(Dimen.ExtraLargePlus)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimen.MediumPlus))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(Dimen.Small)
        ) {
            // Animated background indicator
            val offsetX by animateFloatAsState(
                targetValue = (position.toFloat() / items.size),
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                label = "background_slide"
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1f / items.size)
                    .offset {
                        IntOffset(
                            x = (offsetX * (this@BoxWithConstraints.constraints.maxWidth)).roundToInt(),
                            y = 0
                        )
                    }
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            )

            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                items.forEachIndexed { index, item ->
                    val animatedColor by animateColorAsState(
                        targetValue = if (position == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        animationSpec = tween(durationMillis = 300)
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelLarge,
                        color = animatedColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(MaterialTheme.shapes.medium)
                            .bounceClick {
                                onToggle(index)
                            }
                            .padding(horizontal = Dimen.Small)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AnimatedSelectorPre() {
    AttendanceTheme {
        AnimatedSelector(
            name = "Criteria",
            position = 0,
            items = Criteria.entries.map { it.name },
            onToggle = {}
        )
    }
}

