package name.zzhxufeng.wanandroid.composables

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.web.*
import name.zzhxufeng.wanandroid.ui.theme.WanAndroidTheme

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WanWebView(
    url: String
) {
    WanAndroidTheme {
        var url1 by remember { mutableStateOf(url) }
        val state = rememberWebViewState(url = url1)
        val navigator = rememberWebViewNavigator()

        Column(
            modifier = Modifier.fillMaxSize()
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
                },
                client = webClient
            )
        }
    }
}
