package ir.parkban.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.widget.TextView
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val webView = WebView(this)

            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true

            setContentView(webView)

            webView.loadUrl("http://park-ban.ir/")

        } catch (e: Throwable) {

            val errorText = TextView(this)
            errorText.setTextColor(Color.RED)
            errorText.textSize = 18f
            errorText.setPadding(30, 50, 30, 30)

            errorText.text =
                "خطا در اجرای ParkBan\n\n" +
                "نوع خطا:\n${e.javaClass.name}\n\n" +
                "پیام:\n${e.message}"

            setContentView(errorText)
        }
    }
}
