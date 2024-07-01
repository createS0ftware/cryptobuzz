package uk.co.ht.cryptobuzz.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import uk.co.ht.base.domain.repository.CoinCapRepositoryResult
import uk.co.ht.cryptobuzz.domain.models.CoinData


@Composable
fun CoinCapRepositoryResult<CoinData>.UpdateTextDisplay(
    successContent: @Composable (CoinData) -> Unit,
    modifier: Modifier = Modifier,
    loadingText: String = "Updating...",
    textStyle: TextStyle = MaterialTheme.typography.caption
) {
    when (this) {
        is CoinCapRepositoryResult.Loading -> {
            Text(
                text = loadingText,
                style = textStyle,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        is CoinCapRepositoryResult.Success -> {
            successContent(this.dataObject)
        }

        is CoinCapRepositoryResult.Error -> {
            Text(
                text =   "-",
                style = textStyle,
                color = Color.Red,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}