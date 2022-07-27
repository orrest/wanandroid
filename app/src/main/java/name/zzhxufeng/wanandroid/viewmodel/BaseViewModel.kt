package name.zzhxufeng.wanandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.repository.model.WanResponse
import retrofit2.HttpException

open class BaseViewModel: ViewModel() {
    var errorMsg by mutableStateOf<String?>(null)
        private set

    fun dismissError() { errorMsg = null }

    fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: RuntimeException) {
                errorMsg = e.message
            } catch (e: HttpException) {
                errorMsg = e.message
            } catch (e: Error) {
                errorMsg = e.message
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