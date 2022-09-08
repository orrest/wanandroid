package name.zzhxufeng.wanandroid.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import name.zzhxufeng.wanandroid.ui.model.ThemeColor
import name.zzhxufeng.wanandroid.ui.model.Theme
import name.zzhxufeng.wanandroid.ui.model.ThemeState

private val DarkColorPalette = darkColors(
    primary = primaryDark,
    secondary = secondaryDark,
    background = backgroundDark,
    surface = secondaryDark,
    onPrimary = secondaryDark,
    onSecondary = contentDark,
    onBackground = contentDark,
    onSurface = contentDark,
)

private val WhiteColorPalette = darkColors(
    primary = whitePrimary,
    secondary = whiteSecondary,
    background = whiteBackground,
    surface = whiteSurface,
    onPrimary = whiteOnPrimary,
    onSecondary = whiteOnSecondary,
    onBackground = whiteOnBackground,
    onSurface = whiteOnSurface,
    primaryVariant = whitePrimaryVariant,
    secondaryVariant = whiteSecondaryVariant
)

private val LightBlueColorPalette = lightColors(
    primary = bluePrimary,
    secondary = blueSecondary,
    background = blueBackground,
    surface = blueSurface,
    onPrimary = blueOnPrimary,
    onSecondary = blueOnSecondary,
    onBackground = blueOnBackground,
    onSurface = blueOnSurface,
    primaryVariant = bluePrimaryVariant,
    secondaryVariant = blueSecondaryVariant
)

private val LightOrangeColorPalette = lightColors(
    primary = orangePrimary,
    secondary = orangeSecondary,
    background = orangeBackground,
    surface = orangeSurface,
    onPrimary = orangeOnPrimary,
    onSecondary = orangeOnSecondary,
    onBackground = orangeOnBackground,
    onSurface = orangeOnSurface,
    primaryVariant = orangePrimaryVariant,
    secondaryVariant = orangeSecondaryVariant
)

@Composable
fun WanAndroidTheme(
    theme: ThemeState = Theme.currentTheme.value,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = theme.currentTheme.color
    )

    val colors = if (darkTheme) {
        Log.d("Theme", "recompose dark mode")
        DarkColorPalette
    } else {
        Log.d("Theme", "recompose ${theme.currentTheme.name}")
        when (theme.currentTheme) {
            ThemeColor.WHITE     -> WhiteColorPalette
            ThemeColor.BLUE      -> LightBlueColorPalette
            ThemeColor.ORANGE    -> LightOrangeColorPalette
            ThemeColor.DARK      -> DarkColorPalette
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}