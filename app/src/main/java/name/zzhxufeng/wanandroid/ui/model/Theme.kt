package name.zzhxufeng.wanandroid.ui.model

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.ui.theme.*

object Theme{
    val currentTheme = mutableStateOf(ThemeState())

    fun changeTheme(t: ThemeColor) {
        Log.d("Theme", "changeTheme ${t.name}")
        currentTheme.value = currentTheme.value.copy(currentTheme = t)
    }
}

data class ThemeState(
    val currentTheme: ThemeColor = ThemeColor.WHITE
)

enum class ThemeColor(
    @StringRes val label: Int,
    val color: Color
) {
    WHITE(R.string.theme_white, whitePrimary),
    ORANGE(R.string.theme_orange, orangePrimary),
    BLUE(R.string.theme_blue, bluePrimary),
    DARK(R.string.theme_dark, backgroundDark)
}
