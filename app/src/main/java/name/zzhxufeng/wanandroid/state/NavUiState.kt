package name.zzhxufeng.wanandroid.state

import name.zzhxufeng.wanandroid.data.model.NaviData


data class NavUiState(
    val naviList: List<NaviData> = emptyList(),
) {
    fun getScrollToIndex(index: Int): Int{
        var res = 0
        for (i in 0 until index) {
            res += (naviList[i].articles.size + 2)
        }
        return res
    }
}