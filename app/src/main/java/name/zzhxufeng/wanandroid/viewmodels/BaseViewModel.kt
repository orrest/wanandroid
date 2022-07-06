package name.zzhxufeng.wanandroid.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import name.zzhxufeng.wanandroid.repository.ArticleRefreshError

open class BaseViewModel: ViewModel() {
    /*These states can be used to indicate the progress of the data access.*/
    val spinner = mutableStateOf(false)
    val snackBar = mutableStateOf<String?>(null)

    /**
     * Helper function to call a data load function with a loading spinner, errors will trigger a
     * snackbar.
     *
     * By marking `block` as `suspend` this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling the
     *              lambda the loading spinner will display, after completion or error the loading
     *              spinner will stop
     */
    fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                spinner.value = true
                block()
            } catch (error: ArticleRefreshError) {
                snackBar.value = error.message
            } finally {
                spinner.value = false
            }
        }
    }
}