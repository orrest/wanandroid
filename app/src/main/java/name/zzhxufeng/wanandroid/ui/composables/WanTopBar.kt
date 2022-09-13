package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.utils.TEXT_FONT_SMALL

@Composable
fun WanTopBar(
    desc: String?,
    leftIcon: ImageVector?,
    onLeftClick: () -> Unit = {},
    rightContent: @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        leftIcon?.let {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onLeftClick() }
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = stringResource(R.string.desc_topbar_left_icon),
                )
            }
        }

        desc?.let {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = desc,
                fontSize = TEXT_FONT_SMALL.sp
            )
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            rightContent()
        }
    }
}

