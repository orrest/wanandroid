package name.zzhxufeng.wanandroid.state

import name.zzhxufeng.wanandroid.data.model.HotHistoryModel
import name.zzhxufeng.wanandroid.data.model.SearchResultModel
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API

data class SearchUiState(
    val hotHistory: List<HotHistoryModel>? = null,
    val text: String = "",
    val searchKey: String = "",
    val searchResults: List<SearchResultModel>? = null,
    val nextPage: Int? = START_PAGE_OLD_API
)