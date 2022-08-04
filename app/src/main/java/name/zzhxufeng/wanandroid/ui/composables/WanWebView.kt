package name.zzhxufeng.wanandroid.ui.composables

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import com.google.accompanist.web.*
import name.zzhxufeng.wanandroid.ui.theme.WanAndroidTheme

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WanWebView(
    url: String,
    modifier: Modifier = Modifier,
) {
    WanAndroidTheme {
        var url1 by remember { mutableStateOf(url) }
        val state = rememberWebViewState(url = url1)
        val navigator = rememberWebViewNavigator()

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            val loadingState = state.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // A custom WebViewClient and WebChromeClient can be provided via subclassing
            val webClient = remember {
                object : AccompanistWebViewClient() {
                    override fun onPageStarted(
                        view: WebView?,
                        url: String?,
                        favicon: Bitmap?
                    ) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("Accompanist WebView", "Page started loading for $url")
                    }
                }
            }

            WebView(
                state = state,
                modifier = Modifier.weight(1f),
                navigator = navigator,
                onCreated = { webView ->
                    webView.settings.javaScriptEnabled = true
                    webView.settings.useWideViewPort = true
                    webView.settings.allowFileAccess = true
                    webView.settings.allowContentAccess = true
                    webView.settings.javaScriptCanOpenWindowsAutomatically = true
                    webView.settings.domStorageEnabled = true
                    webView.isHorizontalScrollBarEnabled = true
                },
                client = webClient
            )
        }
    }
}
