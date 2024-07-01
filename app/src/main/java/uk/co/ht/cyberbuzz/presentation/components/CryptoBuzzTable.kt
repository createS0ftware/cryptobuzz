package uk.co.ht.cryptobuzz.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import uk.co.ht.cryptobuzz.domain.models.ExchangeData
import uk.co.ht.cryptobuzz.presentation.theme.MaterialTheme


@Composable
fun TableRow(rowData: ExchangeData, backgroundColour : Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        Text(
            text = rowData.rank.toString(),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = rowData.exchangeName,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = rowData.volumeUsd,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CryptoBuzzTable(data: List<ExchangeData>) {
    var currentRowIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(data) {
        data.forEachIndexed { index, _ ->
            delay(500)  // Delay for each row animation
            currentRowIndex = index + 1
        }
    }
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
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Exchange",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Volume (USD)",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f)
                )
            }
            data.take(currentRowIndex).forEachIndexed { index, row ->

                val backgroundColor = if (index % 2 == 0) MaterialTheme.colors.primary.copy(alpha = 0.1f)
                                        else MaterialTheme.colors.surface

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    TableRow(row, backgroundColor)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedTablePreview() {
    val sampleData = listOf(
        ExchangeData("Binance", 1, "$1.3billion"),
        ExchangeData("Binance", 2, "$1.3billion"),
        ExchangeData("Binance", 3, "$1.3billion"),
    )

    CryptoBuzzTable(data = sampleData)
}