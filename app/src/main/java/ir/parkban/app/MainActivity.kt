package ir.parkban.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.Window
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.view.Gravity

class MainActivity : Activity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        showSplash()
    }

    private fun showSplash() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundColor(Color.WHITE)

        val logo = ImageView(this)

        val logoResId = resources.getIdentifier(
            "parkban_logo",
            "drawable",
            packageName
        )

        if (logoResId != 0) {
            logo.setImageResource(logoResId)
        }

        logo.adjustViewBounds = true

        val logoParams = LinearLayout.LayoutParams(
            650,
            650
        )

        layout.addView(logo, logoParams)

        val title = TextView(this)
        title.text = "پارکبان الکترونیک بوکان"
        title.textSize = 22f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.rgb(20, 55, 100))

        layout.addView(title)

        val loading = ProgressBar(this)
        layout.addView(loading)

        setContentView(layout)

        window.decorView.postDelayed({

            loadWebsite()

        }, 2000)
    }

    private fun loadWebsite() {

        webView = WebView(this)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadsImagesAutomatically = true

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(
                view: WebView?,
                url: String?
            ) {
                setContentView(webView)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                if (request?.isForMainFrame == true) {
                    showError()
                }
            }
        }

        webView.loadUrl("http://park-ban.ir/")
    }

    private fun showError() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40, 40, 40, 40)

        val title = TextView(this)
        title.text = "اتصال برقرار نشد"
        title.textSize = 24f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER

        layout.addView(title)

        val message = TextView(this)
        message.text = "لطفاً اتصال اینترنت دستگاه را بررسی کنید."
        message.textSize = 16f
        message.gravity = Gravity.CENTER
        message.setPadding(0, 20, 0, 30)

        layout.addView(message)

        setContentView(layout)
    }

    override fun onBackPressed() {

        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
