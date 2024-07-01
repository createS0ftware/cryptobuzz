package uk.co.ht.cyberbuzz.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import uk.co.ht.cyberbuzz.domain.models.ExchangeData


@Composable
fun TableRow(rowData: ExchangeData) {
    Row(modifier = Modifier.padding(8.dp)) {
        BasicText(text = rowData.rank.toString(), style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
        BasicText(text = rowData.exchangeName, style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
        BasicText(text = rowData.volumeUsd, style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CyberBuzzTable(data: List<ExchangeData>) {
    var currentRowIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(data) {
        data.forEachIndexed { index, _ ->
            delay(500)  // Delay for each row animation
            currentRowIndex = index + 1
        }
    }

    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            BasicText(text = "Rank", style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
            BasicText(text = "Exchange", style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
            BasicText(text = "Volume (USD)", style = MaterialTheme.typography.caption, modifier = Modifier.weight(1f))
        }
        data.take(currentRowIndex).forEach { row ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                TableRow(row)
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

    CyberBuzzTable(data = sampleData)
}