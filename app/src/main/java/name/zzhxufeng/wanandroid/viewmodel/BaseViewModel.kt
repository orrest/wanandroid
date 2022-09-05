package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.data.model.WanResponse
import name.zzhxufeng.wanandroid.event.MainContainerEvent
import name.zzhxufeng.wanandroid.event.UiEvent
import retrofit2.HttpException

abstract class BaseViewModel: ViewModel() {
    var errorMsg = mutableStateOf<String?>(null)
        private set
    var isRefreshing = mutableStateOf(false)

    fun dismissError() { errorMsg.value = null }

    fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            isRefreshing.value = true
            try {
                block()
            } catch (e: RuntimeException) {
                errorMsg.value = e.message
            } catch (e: HttpException) {
                errorMsg.value = e.message
            } catch (e: Error) {
                errorMsg.value = e.message
            } finally {
                isRefreshing.value = false
            }
        }
    }

    fun <T> tackleResponseError(response: WanResponse<T>?) {
        if (response == null) {
            throw RuntimeException("服务器没有数据响应，请检查网络连接。")
        } else if (response.errorMsg != "") {
            throw RuntimeException(response.errorMsg)
        }
    }

    fun nextPage(curPage: Int, maxPage: Int): Int? {
        return if (curPage + 1 >= maxPage) null
        else curPage + 1
    }
}