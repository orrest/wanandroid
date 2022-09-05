package name.zzhxufeng.wanandroid.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
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
    rightIcon: ImageVector?,
    onLeftIconClick: () -> Unit = {},
    onRightIconClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        leftIcon?.let {
            IconButton(onClick = { onLeftIconClick() }) {
                Image(imageVector = leftIcon, contentDescription = stringResource(R.string.desc_topbar_left_icon))
            }
        }

        desc?.let { Text(text = desc) }

        rightIcon?.let {
            IconButton(onClick = { onRightIconClick() }) {
                Image(imageVector = rightIcon, contentDescription = stringResource(R.string.desc_topbar_right_icon))
            }
        }
    }
}

@Composable
fun WanTopBar(
    desc: String?,
    backIcon: ImageVector?,
    onBackClick: () -> Unit = {},
    rightContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        backIcon?.let {
            IconButton(onClick = { onBackClick() }) {
                Image(
                    imageVector = backIcon,
                    contentDescription = stringResource(R.string.desc_topbar_left_icon)
                )
            }
        }

        desc?.let { Text(
            text = desc,
            fontSize = TEXT_FONT_SMALL.sp
        ) }

        rightContent()
    }
}

