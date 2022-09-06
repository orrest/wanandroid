package name.zzhxufeng.wanandroid.state.drawer

import name.zzhxufeng.wanandroid.data.model.SharingArticleModel
import name.zzhxufeng.wanandroid.data.network.START_PAGE

data class ShareUiState(
    val mySharing: List<SharingArticleModel> = emptyList(),
    val nextPage: Int? = START_PAGE
)