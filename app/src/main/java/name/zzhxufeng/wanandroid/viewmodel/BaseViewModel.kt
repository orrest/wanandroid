package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.data.model.WanResponse
import retrofit2.HttpException

open class BaseViewModel: ViewModel() {
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
}