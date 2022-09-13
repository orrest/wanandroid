package name.zzhxufeng.wanandroid.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.data.network.START_PAGE_OLD_API
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.data.repository.HomeRepository
import name.zzhxufeng.wanandroid.event.SearchEvent
import name.zzhxufeng.wanandroid.state.SearchUiState

class SearchViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(SearchUiState())

    init {
        refreshHotHistory()
    }

    fun handleEvent(event: SearchEvent){
        when(event) {
            SearchEvent.LoadMore -> loadMore()
            is SearchEvent.Search -> search(event.text)
            is SearchEvent.TextChange -> textChange(event.text)
        }
    }

    private fun loadMore() = launchDataLoad{
        val nextPage = uiState.value.nextPage
        if (nextPage != null) {
            val response = HomeRepository.search(nextPage, uiState.value.searchKey)
            if (response.errorCode == WAN_SUCCESS_CODE) {
                uiState.update {
                    it.copy(
                        searchResults = it.searchResults!!.toMutableList().apply {
                            addAll(response.data.datas)
                        }.toList(),
                        nextPage = nextPage(
                            START_PAGE_OLD_API,
                            response.data.curPage,
                            response.data.pageCount
                        ),
                    )
                }
            } else {
                errorMsg.value = response.errorMsg
            }
        } else {
            errorMsg.value = "没有更多数据"
        }
    }

    /*
    * Every time the search() triggered means a new key searching.
    * */
    private fun search(text: String) = launchDataLoad{
        if (text == ""
            || text == uiState.value.searchKey/*This means have triggered once.*/
        ) return@launchDataLoad

        val response = HomeRepository.search(
            START_PAGE_OLD_API,
            text
        )
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                searchResults = response.data.datas,
                searchKey = uiState.value.text,
                nextPage = nextPage(
                    START_PAGE_OLD_API,
                    response.data.curPage,
                    response.data.pageCount
                )
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }

    private fun textChange(text: String) {
        uiState.update { it.copy(
            text = text
        ) }
    }

    private fun refreshHotHistory() = launchDataLoad {
        val response = HomeRepository.fetchHotHistory()
        if (response.errorCode == WAN_SUCCESS_CODE) {
            uiState.update { it.copy(
                hotHistory = response.data
            ) }
        } else {
            errorMsg.value = response.errorMsg
        }
    }
}