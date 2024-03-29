package name.zzhxufeng.wanandroid.ui.screens.drawer.items.sharing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.SharingArticleModel
import name.zzhxufeng.wanandroid.event.drawer.SharingEvent
import name.zzhxufeng.wanandroid.state.drawer.ShareUiState
import name.zzhxufeng.wanandroid.ui.composables.EmptyContent
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.ui.composables.WanTopBar
import name.zzhxufeng.wanandroid.utils.ITEM_PADDING
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun Sharings(
    uiState: ShareUiState,
    handleEvent: (SharingEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToPostShare: () -> Unit,
) {
    Scaffold(
        topBar = {
            WanTopBar(
                desc = stringResource(id = R.string.title_share),
                leftIcon = Icons.Default.ArrowBack,
                onLeftClick = navigateBack
            )
        },
        floatingActionButton = { SharingFloatingButton(navigateToPostShare) },
    ) {
        val padding = it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SCREEN_PADDING.dp)
        ) {
            if (uiState.mySharing.isNotEmpty()) {
                SharingContent(uiState, handleEvent)
            } else {
                EmptyContent()
            }
        }
    }
}

@Composable
fun SharingContent(
    uiState: ShareUiState,
    handleEvent: (SharingEvent) -> Unit
) {
    LazyColumn {
        itemsIndexed(uiState.mySharing) { index, item ->
            SharingItem(sharingModel = item, handleEvent = handleEvent)

            LaunchedEffect(key1 = uiState.nextPage, block = {
                if (uiState.mySharing.size - index == 3) {
                    handleEvent(SharingEvent.LoadMore())
                }
            })
        }
    }
}

@Composable
fun SharingItem(
    sharingModel: SharingArticleModel,
    handleEvent: (SharingEvent) -> Unit
) {
    WanCard(onClick = { /*no*/ }) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = sharingModel.shareUser)
                    Spacer(modifier = Modifier.width(ITEM_PADDING.dp))
                    Text(text = if (sharingModel.fresh) "新" else "")
                }
                Text(text = sharingModel.niceDate)
            }
            Text(text = sharingModel.title)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${sharingModel.superChapterName} · ${sharingModel.chapterName}")
                Image(
                    imageVector = Icons.Default.CollectionsBookmark,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        handleEvent(SharingEvent.AddBookmark(articleId = sharingModel.id))
                    }
                )
            }
        }
    }
}

@Composable
fun SharingFloatingButton(
    onClick: () -> Unit
) {
    Image(
        modifier = Modifier.clickable { onClick() },
        imageVector = Icons.Default.AddCircle,
        contentDescription = null
    )
}