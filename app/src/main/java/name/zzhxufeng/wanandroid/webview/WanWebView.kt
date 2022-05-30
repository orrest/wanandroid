package name.zzhxufeng.wanandroid.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.*
import name.zzhxufeng.wanandroid.ui.theme.WanandroidTheme

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WanWebView(
    url: String = "https://google.com"
) {
    WanandroidTheme {
        var url1 by remember { mutableStateOf(url) }
        val state = rememberWebViewState(url = url1)
        val navigator = rememberWebViewNavigator()
        var textFieldValue by remember(state.content.getCurrentUrl()) {
            mutableStateOf(state.content.getCurrentUrl() ?: "")
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar {
                Text("adasfd")
            }
            Row {
                Box(modifier = Modifier.weight(1f)) {
                    if (state.errorsForCurrentRequest.isNotEmpty()) {
                        Image(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        url1 = textFieldValue
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Go")
                }
            }

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
                },
                client = webClient
            )
        }
    }
}
