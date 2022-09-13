package name.zzhxufeng.wanandroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import name.zzhxufeng.wanandroid.R
import name.zzhxufeng.wanandroid.data.model.SearchResultModel
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.event.SearchEvent
import name.zzhxufeng.wanandroid.state.SearchUiState
import name.zzhxufeng.wanandroid.ui.composables.ArticleItem
import name.zzhxufeng.wanandroid.ui.composables.WanCard
import name.zzhxufeng.wanandroid.utils.ITEM_PADDING
import name.zzhxufeng.wanandroid.utils.SCREEN_PADDING

@Composable
fun WanSearch(
    uiState: SearchUiState,
    onBackClick: () -> Unit,
    handleEvent: (SearchEvent) -> Unit,
    onArticleClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                text = uiState.text,
                onTextChange = { handleEvent(SearchEvent.TextChange(it)) },
                onSearchClick = { handleEvent(SearchEvent.Search(it)) },
                onBackClick = { onBackClick() },
            )
        }
    ) {
        val padding = it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SCREEN_PADDING.dp)
        ) {
            if (uiState.searchResults == null) {
                Text(text = stringResource(R.string.label_hot_history))
                FlowRow {
                    uiState.hotHistory?.forEach {
                        WanCard(
                            modifier = Modifier.padding(horizontal = ITEM_PADDING.dp),
                            onClick = { handleEvent(SearchEvent.Search(it.name)) }
                        ) {
                            Text(text = it.name)
                        }
                    }
                }
            } else {
                LazyColumn{
                    itemsIndexed(uiState.searchResults){ index, item ->
                        SearchResultItem(model = item, onClick = onArticleClick)

                        LaunchedEffect(key1 = uiState.nextPage, block = {
                            if (uiState.searchResults.size - index == 3) {
                                handleEvent(SearchEvent.LoadMore)
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    model: SearchResultModel,
    onClick: (String) -> Unit
) {
    WanCard(onClick = { onClick(model.link) }) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                model.tags.forEach {
                    Text(text = it.name)
                }
                Text(text = model.author)
                Text(text = model.niceDate)
            }
            Text(text = model.title)
            Text(text = "${model.superChapterName}/${model.chapterName}")
        }
    }
}

@Composable
private fun SearchTopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { onBackClick() }
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }

        TextField(
            modifier = Modifier.align(Alignment.Center),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = { Text(text = stringResource(R.string.indicator_search)) },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    onSearchClick(text)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { onSearchClick(text) }
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        }
    }
}
