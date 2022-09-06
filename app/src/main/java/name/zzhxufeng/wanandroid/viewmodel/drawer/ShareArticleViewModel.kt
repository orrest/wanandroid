package name.zzhxufeng.wanandroid.viewmodel.drawer

import android.widget.Toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import name.zzhxufeng.wanandroid.WanApplication
import name.zzhxufeng.wanandroid.data.network.WAN_SUCCESS_CODE
import name.zzhxufeng.wanandroid.data.repository.DrawerRepository
import name.zzhxufeng.wanandroid.event.drawer.ShareArticleEvent
import name.zzhxufeng.wanandroid.state.drawer.ShareArticleUiState
import name.zzhxufeng.wanandroid.viewmodel.BaseViewModel

class ShareArticleViewModel: BaseViewModel() {
    val uiState = MutableStateFlow(ShareArticleUiState())

    fun handleEvent(event: ShareArticleEvent) {
        when(event) {
            is ShareArticleEvent.ShareArticle -> { shareArticle() }
            is ShareArticleEvent.TitleChange -> { titleChange(event.title) }
            is ShareArticleEvent.LinkChange -> { linkChange(event.link) }
        }
    }

    private fun linkChange(link: String) {
        if (link == ""){
            uiState.update { it.copy(
                link = null,
                canPost = false
            ) }
        } else {
            uiState.update { it.copy(
                link = link,
                canPost = uiState.value.canPost()
            ) }
        }
    }

    private fun titleChange(title: String) {
        if (title == "") {
            uiState.update { it.copy(
                title = null,
                canPost = false
            ) }
        } else {
            uiState.update { it.copy(
                title = title,
                canPost = uiState.value.canPost()
            ) }
        }
    }

    private fun shareArticle() = launchDataLoad {
        val response = DrawerRepository.shareArticle(
            title = uiState.value.title!!,
            link = uiState.value.link!!
        )
        if (response.errorCode == WAN_SUCCESS_CODE) {
            Toast.makeText(
                WanApplication.instance?.applicationContext, "分享成功", Toast.LENGTH_SHORT
            ).show()
        } else {
            errorMsg.value = response.errorMsg
        }
    }
}