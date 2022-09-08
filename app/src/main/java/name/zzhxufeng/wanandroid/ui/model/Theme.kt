package name.zzhxufeng.wanandroid.ui.model

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.ui.theme.black
import name.zzhxufeng.wanandroid.ui.theme.blue500
import name.zzhxufeng.wanandroid.ui.theme.orange500
import name.zzhxufeng.wanandroid.ui.theme.white

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
    WHITE(R.string.theme_white, white),
    ORANGE(R.string.theme_orange, orange500),
    BLUE(R.string.theme_blue, blue500),
    DARK(R.string.theme_dark, black)
}
