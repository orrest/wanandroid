package name.zzhxufeng.wanandroid.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import name.zzhxufeng.wanandroid.ui.model.ThemeColor
import name.zzhxufeng.wanandroid.ui.model.Theme
import name.zzhxufeng.wanandroid.ui.model.ThemeState

private val DarkColorPalette = darkColors(
    primary = grayBackground,
    secondary = grayBackground,
    background = grayContainer,
    surface = grayContainer,
    onPrimary = grayOnContainer,
    onSecondary = grayOnContainer,
    onBackground = lightGrayBackground,
    onSurface = lightGrayBackground,
)

private val WhiteColorPalette = darkColors(
    primary = lightGrayBackground,
    secondary = lightGrayBackground,
    background = lightGrayContainer,
    surface = lightGrayContainer,
    onPrimary = lightGrayOnContainer,
    onSecondary = lightGrayOnContainer,
    onBackground = black,
    onSurface = black,
)

private val LightBlueColorPalette = lightColors(
    primary = blue500,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val LightOrangeColorPalette = lightColors(
    primary = orange500,
    secondary = teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun WanAndroidTheme(
    theme: ThemeState = Theme.currentTheme.value,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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