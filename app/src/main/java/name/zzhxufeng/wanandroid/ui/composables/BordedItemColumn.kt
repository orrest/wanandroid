package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.utils.ITEM_PADDING
import name.zzhxufeng.wanandroid.utils.LINE_WIDTH_THIN
import name.zzhxufeng.wanandroid.utils.ROUNDED_CORNER

@Composable
fun BorderedItemColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(ROUNDED_CORNER.dp),
                color = Color.Transparent
            )
            .padding(vertical = ITEM_PADDING.dp)
            .border(width = LINE_WIDTH_THIN.dp, color = Color.Black),
    ) {
        content()
    }
}