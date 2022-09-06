package name.zzhxufeng.wanandroid.state.drawer

import name.zzhxufeng.wanandroid.data.model.BookmarkModel
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API

data class BookmarkUiState(
    val bookmarks: List<BookmarkModel> = emptyList(),
    val nextPage: Int? = START_PAGE_OLD_API
)