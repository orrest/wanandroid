package name.zzhxufeng.wanandroid.ui.model

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WanScreen(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .fillMaxHeight(),
        color = MaterialTheme.colors.surface
    ) {
        content()
    }
}

@Preview
@Composable
fun WanScreenPreview() {
    WanScreen {
        Box(modifier = Modifier.fillMaxWidth().background(Color.Transparent))
    }
}