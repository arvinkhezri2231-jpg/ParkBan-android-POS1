package ir.parkban.app

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(applicationContext)

        webView.webViewClient = WebViewClient()

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        setContentView(webView)

        webView.loadUrl("http://park-ban.ir/")
    }
}
