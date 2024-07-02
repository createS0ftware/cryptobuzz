import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import uk.co.ht.cryptobuzz.presentation.theme.FunctionalRed
import uk.co.ht.cryptobuzz.presentation.theme.FunctionalRedDark
import uk.co.ht.cryptobuzz.presentation.theme.GoldenHue
import uk.co.ht.cryptobuzz.presentation.theme.Neutral0
import uk.co.ht.cryptobuzz.presentation.theme.Neutral1
import uk.co.ht.cryptobuzz.presentation.theme.Neutral4
import uk.co.ht.cryptobuzz.presentation.theme.Neutral5
import uk.co.ht.cryptobuzz.presentation.theme.Neutral7
import uk.co.ht.cryptobuzz.presentation.theme.Neutral8
import uk.co.ht.cryptobuzz.presentation.theme.Ocean0
import uk.co.ht.cryptobuzz.presentation.theme.Ocean1
import uk.co.ht.cryptobuzz.presentation.theme.Ocean10
import uk.co.ht.cryptobuzz.presentation.theme.Ocean11
import uk.co.ht.cryptobuzz.presentation.theme.Ocean2
import uk.co.ht.cryptobuzz.presentation.theme.Ocean3
import uk.co.ht.cryptobuzz.presentation.theme.Ocean4
import uk.co.ht.cryptobuzz.presentation.theme.Ocean5
import uk.co.ht.cryptobuzz.presentation.theme.Ocean6
import uk.co.ht.cryptobuzz.presentation.theme.Ocean7
import uk.co.ht.cryptobuzz.presentation.theme.Ocean8
import uk.co.ht.cryptobuzz.presentation.theme.Ocean9

@Stable
class CryptoBuzzColours(
    gradient6_1: List<Color>,
    gradient6_2: List<Color>,
    gradient3_1: List<Color>,
    gradient3_2: List<Color>,
    gradient2_1: List<Color>,
    gradient2_2: List<Color>,
    gradient2_3: List<Color>,
    brand: Color,
    brandSecondary: Color,
    uiBackground: Color,
    uiBorder: Color,
    uiFloated: Color,
    interactivePrimary: List<Color> = gradient2_1,
    interactiveSecondary: List<Color> = gradient2_2,
    interactiveMask: List<Color> = gradient6_1,
    textPrimary: Color = brand,
    textSecondary: Color,
    textHelp: Color,
    textInteractive: Color,
    textLink: Color,
    tornado1: List<Color>,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    iconInteractive: Color,
    iconInteractiveInactive: Color,
    error: Color,
    notificationBadge: Color = error,
    isDark: Boolean,
    goldHue: Color
) {
    var gradient6_1 by mutableStateOf(gradient6_1)
        private set
    var gradient6_2 by mutableStateOf(gradient6_2)
        private set
    var gradient3_1 by mutableStateOf(gradient3_1)
        private set
    var gradient3_2 by mutableStateOf(gradient3_2)
        private set
    var gradient2_1 by mutableStateOf(gradient2_1)
        private set
    var gradient2_2 by mutableStateOf(gradient2_2)
        private set
    var gradient2_3 by mutableStateOf(gradient2_3)
        private set
    var brand by mutableStateOf(brand)
        private set
    var brandSecondary by mutableStateOf(brandSecondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var uiFloated by mutableStateOf(uiFloated)
        private set
    var interactivePrimary by mutableStateOf(interactivePrimary)
        private set
    var interactiveSecondary by mutableStateOf(interactiveSecondary)
        private set
    var interactiveMask by mutableStateOf(interactiveMask)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set
    var textInteractive by mutableStateOf(textInteractive)
        private set
    var tornado1 by mutableStateOf(tornado1)
        private set
    var textLink by mutableStateOf(textLink)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var iconInteractive by mutableStateOf(iconInteractive)
        private set
    var iconInteractiveInactive by mutableStateOf(iconInteractiveInactive)
        private set
    var error by mutableStateOf(error)
        private set
    var notificationBadge by mutableStateOf(notificationBadge)
        private set
    var isDark by mutableStateOf(isDark)
        private set
    var goldHue by mutableStateOf(goldHue)
        private set

    fun update(other: CryptoBuzzColours) {
        gradient6_1 = other.gradient6_1
        gradient6_2 = other.gradient6_2
        gradient3_1 = other.gradient3_1
        gradient3_2 = other.gradient3_2
        gradient2_1 = other.gradient2_1
        gradient2_2 = other.gradient2_2
        gradient2_3 = other.gradient2_3
        brand = other.brand
        brandSecondary = other.brandSecondary
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        uiFloated = other.uiFloated
        interactivePrimary = other.interactivePrimary
        interactiveSecondary = other.interactiveSecondary
        interactiveMask = other.interactiveMask
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textHelp = other.textHelp
        textInteractive = other.textInteractive
        textLink = other.textLink
        tornado1 = other.tornado1
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        iconInteractive = other.iconInteractive
        iconInteractiveInactive = other.iconInteractiveInactive
        error = other.error
        notificationBadge = other.notificationBadge
        isDark = other.isDark
        goldHue = other.goldHue
    }

    fun copy(): CryptoBuzzColours = CryptoBuzzColours(
        gradient6_1 = gradient6_1,
        gradient6_2 = gradient6_2,
        gradient3_1 = gradient3_1,
        gradient3_2 = gradient3_2,
        gradient2_1 = gradient2_1,
        gradient2_2 = gradient2_2,
        gradient2_3 = gradient2_3,
        brand = brand,
        brandSecondary = brandSecondary,
        uiBackground = uiBackground,
        uiBorder = uiBorder,
        uiFloated = uiFloated,
        interactivePrimary = interactivePrimary,
        interactiveSecondary = interactiveSecondary,
        interactiveMask = interactiveMask,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textHelp = textHelp,
        textInteractive = textInteractive,
        textLink = textLink,
        tornado1 = tornado1,
        iconPrimary = iconPrimary,
        iconSecondary = iconSecondary,
        iconInteractive = iconInteractive,
        iconInteractiveInactive = iconInteractiveInactive,
        error = error,
        notificationBadge = notificationBadge,
        isDark = isDark,
        goldHue = GoldenHue,
    )
}


val LightCryptoBuzzColours = CryptoBuzzColours(
    gradient6_1 = listOf(Ocean11, Ocean10, Ocean9, Ocean8, Ocean7, Ocean6),
    gradient6_2 = listOf(Ocean5, Ocean4, Ocean3, Ocean2, Ocean1, Ocean0),
    gradient3_1 = listOf(Ocean11, Ocean10, Ocean9),
    gradient3_2 = listOf(Ocean8, Ocean7, Ocean6),
    gradient2_1 = listOf(Ocean5, Ocean4),
    gradient2_2 = listOf(Ocean3, Ocean2),
    gradient2_3 = listOf(Ocean1, Ocean0),
    brand = Ocean7,
    brandSecondary = Ocean5,
    uiBackground = Neutral0,
    uiBorder = Neutral4,
    uiFloated = Neutral1,
    textPrimary = Ocean11,
    textSecondary = Ocean10,
    textHelp = Ocean9,
    textInteractive = Ocean8,
    textLink = Ocean7,
    tornado1 = listOf(Ocean6),
    iconPrimary = Ocean7,
    iconSecondary = Ocean5,
    iconInteractive = Ocean4,
    iconInteractiveInactive = Ocean3,
    error = FunctionalRed,
    notificationBadge = FunctionalRed,
    interactivePrimary = listOf(Ocean5, Ocean4),
    interactiveSecondary = listOf(Ocean3, Ocean2),
    interactiveMask = listOf(Ocean11, Ocean10),
    isDark = false,
    goldHue = GoldenHue
)

val DarkCryptoBuzzColours = CryptoBuzzColours(
    gradient6_1 = listOf(Ocean11, Ocean10, Ocean9, Ocean8, Ocean7, Ocean6),
    gradient6_2 = listOf(Ocean5, Ocean4, Ocean3, Ocean2, Ocean1, Ocean0),
    gradient3_1 = listOf(Ocean11, Ocean10, Ocean9),
    gradient3_2 = listOf(Ocean8, Ocean7, Ocean6),
    gradient2_1 = listOf(Ocean5, Ocean4),
    gradient2_2 = listOf(Ocean3, Ocean2),
    gradient2_3 = listOf(Ocean1, Ocean0),
    brand = Ocean7,
    brandSecondary = Ocean5,
    uiBackground = Neutral8,
    uiBorder = Neutral5,
    uiFloated = Neutral7,
    interactivePrimary = listOf(Ocean5, Ocean4),
    interactiveSecondary = listOf(Ocean3, Ocean2),
    interactiveMask = listOf(Ocean11, Ocean10),
    textPrimary = Neutral0,
    textSecondary = Ocean1,
    textHelp = Ocean2,
    textInteractive = Ocean3,
    textLink = Ocean4,
    tornado1 = listOf(Ocean5),
    iconPrimary = Ocean7,
    iconSecondary = Ocean5,
    iconInteractive = Ocean4,
    iconInteractiveInactive = Ocean3,
    error = FunctionalRedDark,
    notificationBadge = FunctionalRedDark,
    isDark = true,
    goldHue = GoldenHue
)

val LocalCryptoBuzzColours = staticCompositionLocalOf {
    LightCryptoBuzzColours
}

@Composable
fun CryptoBuzzTheme(
    colours: CryptoBuzzColours = LightCryptoBuzzColours,
    content: @Composable () -> Unit
) {
    val colors = if (colours.isDark) {
        darkColors(
            primary = colours.brand,
            primaryVariant = colours.brandSecondary,
            secondary = colours.textPrimary,
            background = colours.uiBackground,
            surface = colours.uiFloated,
            onPrimary = colours.uiBackground,
            onSecondary = colours.uiBackground,
            onBackground = colours.textPrimary,
            onSurface = colours.textPrimary
        )
    } else {
        lightColors(
            primary = colours.brand,
            primaryVariant = colours.brandSecondary,
            secondary = colours.textPrimary,
            background = colours.uiBackground,
            surface = colours.uiFloated,
            onPrimary = colours.uiBackground,
            onSecondary = colours.uiBackground,
            onBackground = colours.textPrimary,
            onSurface = colours.textPrimary
        )
    }

    CompositionLocalProvider(LocalCryptoBuzzColours provides colours) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}

object CryptoBuzzTheme {
    val colours: CryptoBuzzColours
        @Composable
        get() = LocalCryptoBuzzColours.current
}
