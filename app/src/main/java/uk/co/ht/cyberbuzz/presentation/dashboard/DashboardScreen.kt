package uk.co.ht.cryptobuzz.presentation.dashboard

import CryptoBuzzTheme
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.ht.cryptobuzz.R
import uk.co.ht.cryptobuzz.domain.models.AssetInfoData
import uk.co.ht.cryptobuzz.presentation.UIState
import uk.co.ht.cryptobuzz.presentation.components.CoinDataImage
import uk.co.ht.cryptobuzz.presentation.components.DataUICard
import uk.co.ht.cryptobuzz.presentation.components.UpdateImageDisplay
import uk.co.ht.cryptobuzz.presentation.components.UpdateTextDisplay
import uk.co.ht.cyberbuzz.common.utils.getDrawableIdByName

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

@Composable
fun DashboardScreen(
    dashboardViewModel: DashboardViewModel? = hiltViewModel()
) {
    val isDarkTheme = isSystemInDarkTheme()
    var state by remember {
        mutableStateOf(UIState.Loading)
    }

    val coroutineScope = rememberCoroutineScope()
    Surface(
        color = if (isDarkTheme) Color.Black else Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedContent(
            state,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(3000)
                ) togetherWith fadeOut(animationSpec = tween(3000))
            },
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                state = when (state) {
                    UIState.Loading -> UIState.Loaded
                    UIState.Loaded -> UIState.Error
                    UIState.Error -> UIState.Loading
                }
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {
                UIState.Loading -> {

                }

                UIState.Loaded -> {

                }

                UIState.Error -> {

                }
            }
            DashboardGrid(
                dashboardViewModel
            )
        }
    }
}

@Composable
fun DashboardGrid(
    dashboardViewModel: DashboardViewModel? = null
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val spacing = 8.dp
    val totalSpacing =
        (spacing) + (8.dp * 2)
    val cellWidth = (screenWidth - totalSpacing) / 2

    dashboardViewModel?.getTopCoinAsset()

    val primaryCoinDataState by dashboardViewModel?.primaryAssetStateFlow!!.collectAsState()
    val secondaryCoinDataState by dashboardViewModel?.secondaryAssetStateFlow!!.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Black))
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 40.0.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DataUICard(
                    modifier = Modifier
                        .size(
                            width = 170.dp,
                            height = 150.dp
                        )
                        .padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .height(120.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                            )
                            primaryCoinDataState.UpdateImageDisplay(successContent =
                            { assetInfoData ->
                                CryptoDataImage(
                                    name = assetInfoData.assetName,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .align(Alignment.Center)
                                )
                            }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .fillMaxWidth()
                                .padding(start = 20.0.dp)
                        ) {
                            primaryCoinDataState
                                .UpdateTextDisplay(
                                    successContent = { assetInfoData ->
                                        Text(
                                            text = assetInfoData.assetName,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                                            color = MaterialTheme.colors.primary
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    textStyle = MaterialTheme.typography.subtitle2
                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            primaryCoinDataState
                                .UpdateTextDisplay(
                                    successContent = { assetInfoData ->
                                        Text(
                                            text = assetInfoData.assetValue,
                                            maxLines = 1,
                                            textAlign = TextAlign.End,
                                            overflow = TextOverflow.Ellipsis,
                                            lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                                            color = MaterialTheme.colors.primary
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp),
                                    textStyle = MaterialTheme.typography.subtitle2
                                )
                        }

                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                DataUICard(
                    modifier = Modifier
                        .size(
                            width = 170.dp,
                            height = 150.dp
                        )
                        .padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .clickable(onClick = {})
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .height(120.dp)
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                            )
                            secondaryCoinDataState.UpdateImageDisplay(
                                successContent =
                                { assetInfoData ->
                                    CryptoDataImage(
                                        name = assetInfoData.assetName,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .fillMaxWidth()
                        ) {
                            secondaryCoinDataState
                                .UpdateTextDisplay(
                                    successContent = { assetInfoData ->
                                        Text(
                                            text = assetInfoData.assetName,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                                            color = MaterialTheme.colors.primaryVariant
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    textStyle = MaterialTheme.typography.h5
                                )
                            Spacer(modifier = Modifier.width(8.dp))
                            secondaryCoinDataState
                                .UpdateTextDisplay(
                                    successContent = { assetInfoData ->
                                        Text(
                                            text = assetInfoData.assetValue,
                                            maxLines = 1,
                                            textAlign = TextAlign.End,
                                            overflow = TextOverflow.Ellipsis,
                                            lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                                            color = MaterialTheme.colors.secondary
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 6.dp),
                                    textStyle = MaterialTheme.typography.h5
                                )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DataUICard(
                    modifier = Modifier
                        .size(
                            width = 170.dp,
                            height = 150.dp
                        )
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable(onClick = {
                                dashboardViewModel?.clickedExchanges()
                            })
                            .fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .padding(4.0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            )
                            CoinDataImage(
                                drawableId = R.drawable.exchangeten,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(140.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                DataUICard(
                    modifier = Modifier
                        .size(
                            width = 170.dp,
                            height = 150.dp
                        )
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable(onClick = {
                                dashboardViewModel?.clickedCoins()
                            })
                            .fillMaxSize()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(CryptoBuzzTheme.colours.goldHue)
                                .padding(4.0.dp)
                        ) {
                            Box(
                                modifier = Modifier

                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                            )
                            CoinDataImage(
                                drawableId = R.drawable.coinsten,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(140.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun HighlightedCoinDataVIew(
    index: Int,
    coinDataModelItems: List<AssetInfoData>,
    onSnackClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState(0)
    val gradient = when ((index / 2) % 2) {
        0 -> MaterialTheme.colors.onSurface
        else -> MaterialTheme.colors.onSecondary
    }
    // The Cards show a gradient which spans 3 cards and scrolls with parallax.
    val gradientWidth = with(LocalDensity.current) {
        (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(coinDataModelItems) { index, assetInfoData ->
            if (index < coinDataModelItems.count()) {
                HighlightedCoinDataUIItem(
                    assetInfoData,
                    onSnackClick,
                    index
                )
            }
        }
    }
}

@Composable
private fun HighlightedCoinDataUIItem(
    assetInfoData: AssetInfoData,
    onSnackClick: (String) -> Unit,
    index: Int,
    modifier: Modifier = Modifier
) {
    val left = index * with(LocalDensity.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }
    DataUICard(
        modifier = modifier
            .size(
                width = 170.dp,
                height = 150.dp
            )
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Color.hsl(214f, 0.51f, 0.52f, 1f, ColorSpaces.LinearSrgb),
                    RectangleShape
                )
                .clickable(onClick = {
                })
                .fillMaxSize()
        ) {
            Box(

                modifier = Modifier
                    .border(width = 0.5.dp, color = Color.Black)
                    .height(120.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .border(width = 0.5.dp, color = Color.Red)
                        .height(120.dp)
                        .fillMaxWidth()
                )
                CryptoDataImage(
                    name = assetInfoData.assetName,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }
            Row(
                modifier = Modifier
                    .border(width = 1.0.dp, color = Color.Yellow)
                    .fillMaxWidth()
            ) {
                Text(
                    text = assetInfoData.assetName,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = assetInfoData.assetValue,
                    style = MaterialTheme.typography.caption,
                    lineHeight = TextUnit(16.0F, TextUnitType.Sp),
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(horizontal = 6.dp)
                )
            }

        }
    }
}

@Composable
fun CryptoDataImage(name: String, modifier: Modifier = Modifier) {
    val drawableId = getDrawableIdByName(name)
    CoinDataImage(
        drawableId = drawableId,
        contentDescription = null,
        modifier = modifier
    )
}


@Preview
@Composable
fun HighlightedCoinDataUIItemPreview() {

    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .border(width = 1.0.dp, color = Color.Yellow)
                .fillMaxWidth()
                .padding(vertical = 8.dp)

        ) {

            HighlightedCoinDataUIItem(
                AssetInfoData("Coin One", 3, "bitcoin"),
                onSnackClick = {}, 0,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
    }
}
