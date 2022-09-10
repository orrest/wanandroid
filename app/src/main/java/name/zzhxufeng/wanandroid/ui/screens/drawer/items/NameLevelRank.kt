package name.zzhxufeng.wanandroid.ui.screens.drawer.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import name.zzhxufeng.wanandroid.utils.TEXT_FONT_MEDIUM

@Composable
fun NameLevelRank(
    name: String?,
    level: Int?,
    rank: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name?: "name",
            fontSize = TEXT_FONT_MEDIUM.sp
        )
        Text(text = "等级 ${level ?: ""}  |  排名 ${rank ?: ""}")
    }
}
