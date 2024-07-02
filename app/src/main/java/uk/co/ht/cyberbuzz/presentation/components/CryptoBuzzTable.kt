package uk.co.ht.cryptobuzz.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData


@Composable
fun TableRow(rowData: AssetInfoData, backgroundColour: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = rowData.assetRank.toString(),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = rowData.assetName,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = rowData.assetValue,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1.5f)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CryptoBuzzTable(data: List<AssetInfoData>) {
    var currentRowIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(data) {
        data.forEachIndexed { index, _ ->
            delay(100)  // Delay for each row animation
            currentRowIndex = index + 1
        }
    }
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 1500,
                easing = LinearEasing
            )
        ),
        exit = fadeOut()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .border(
                        1.dp,
                        SolidColor(MaterialTheme.colors.onBackground),
                        RoundedCornerShape(4.dp)
                    )
                    .background(MaterialTheme.colors.surface)
                    .verticalScroll(rememberScrollState())
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Rank",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(0.5f)
                    )
                    Text(
                        text = "Exchange",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Volume (USD)",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1.5f)
                    )
                }
                data.take(currentRowIndex)
                    .forEachIndexed { index, row ->
                        val backgroundColor =
                            if (index % 2 == 0) MaterialTheme.colors.onBackground.copy(alpha = 0.1f)
                            else MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            ) + expandVertically(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            ),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            TableRow(row, backgroundColor)
                        }
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedTablePreview() {
    val sampleData = listOf(
        AssetInfoData("Binance", 1, "$1.3billion"),
        AssetInfoData("Binance", 2, "$1.3billion"),
        AssetInfoData("Binance", 3, "$1.3billion"),
    )

    CryptoBuzzTable(data = sampleData)
}