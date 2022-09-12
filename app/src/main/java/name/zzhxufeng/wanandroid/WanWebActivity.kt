package name.zzhxufeng.wanandroid

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.web.*
import name.zzhxufeng.wanandroid.ui.theme.WanAndroidTheme

const val URL = "url"

class WanWebActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(URL)
        setContent {
            WanAndroidTheme {
                WanWebView(url!!)
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WanWebView(
    url: String,
    modifier: Modifier = Modifier,
) {
    val state = rememberWebViewState(url = url)
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
