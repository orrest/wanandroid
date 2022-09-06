package name.zzhxufeng.wanandroid.ui.screens.drawer.items.sharing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.event.drawer.ShareArticleEvent
import name.zzhxufeng.wanandroid.state.drawer.ShareArticleUiState
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun SharingAdd(
    uiState: ShareArticleUiState,
    handleEvent: (ShareArticleEvent) -> Unit,
    navigateBack: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = SCREEN_PADDING.dp)) {
        WanTopBar(
            desc = stringResource(id = R.string.title_share),
            backIcon = Icons.Default.ArrowBack,
            onBackClick = navigateBack
        )
        Text(text = stringResource(R.string.label_title))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.title ?: "",
            onValueChange = { string-> handleEvent(ShareArticleEvent.TitleChange(string)) },
            placeholder = { Text(text = stringResource(R.string.label_title_placeholder)) },
            singleLine = true
        )

        Text(text = stringResource(R.string.label_link))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.link ?: "",
            onValueChange = { string-> handleEvent(ShareArticleEvent.LinkChange(string)) },
            placeholder = { Text(text = stringResource(R.string.label_link_placeholder)) },
            singleLine = true
        )

        TextButton(
            enabled = uiState.canPost,
            onClick = { handleEvent(ShareArticleEvent.ShareArticle()) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(R.string.label_post))
        }

        Divider()

        Text(text = stringResource(R.string.label_introduction))

        Text(
            text = "1. 只要是任何好文都可以分享哈，并不一定要是原创！投递的文章会进入广场 tab;\n" +
                    "2. CSDN，掘金，简书等官方博客站点会直接通过，不需要审核;\n" +
                    "3. 其他个人站点会进入审核阶段，不要投递任何无效链接，测试的请尽快删除，否则可能会对你的账号产生一定影响;\n" +
                    "4. 目前处于测试阶段，如果你发现500等错误，可以向我提交日志，让我们一起使网站变得更好。\n" +
                    "5. 由于本站只有我一个人开发与维护，会尽力保证24小时内审核，当然有可能哪天太累，会延期，请保持佛系..."
        )
    }
}