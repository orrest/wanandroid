package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmptyContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "暂无数据")
    }
}