package name.zzhxufeng.wanandroid.ui.screens.drawer.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.BookmarkModel
import name.zzhxufeng.wanandroid.event.BookmarkEvent
import name.zzhxufeng.wanandroid.state.drawer.BookmarkUiState
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun Bookmarks(
    uiState: BookmarkUiState,
    handleEvent: (BookmarkEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToBookmarkPage: (String) -> Unit
) {
    Scaffold(
        topBar = {
            WanTopBar(
                desc = stringResource(id = R.string.title_bookmarks),
                leftIcon = Icons.Default.ArrowBack,
                onLeftClick = navigateBack
            )
        }
    ) {
        val padding = it

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(SCREEN_PADDING.dp)
        ) {
            itemsIndexed(uiState.bookmarks) { index, item ->
                BookmarkItem(item, navigateToBookmarkPage)

                LaunchedEffect(key1 = uiState.nextPage, block = {
                    if (uiState.bookmarks.size - index == 3) {
                        handleEvent(BookmarkEvent.LoadMore)
                    }
                })
            }
        }
    }
}

@Composable
fun BookmarkItem(
    bookmarkModel: BookmarkModel,
    onClick: (String) -> Unit
) {
    WanCard(
        onClick = { onClick(bookmarkModel.link) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = bookmarkModel.title,
            )
            Text(
                text = "作者：${if(bookmarkModel.author == "") "匿名" else bookmarkModel.author}\t" +
                        "发布时间：${bookmarkModel.niceDate}",
            )
        }
    }
}
