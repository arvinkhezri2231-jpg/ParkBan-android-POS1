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
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.view.Gravity

class MainActivity : Activity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // تمام صفحه
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        showLoading()
    }

    private fun showLoading() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundColor(Color.WHITE)

        val title = TextView(this)
        title.text = "ParkBan"
        title.textSize = 28f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER

        val subtitle = TextView(this)
        subtitle.text = "در حال اتصال..."
        subtitle.textSize = 16f
        subtitle.gravity = Gravity.CENTER
        subtitle.setPadding(0, 15, 0, 30)

        progressBar = ProgressBar(this)

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(progressBar)

        setContentView(layout)

        loadWebsite()
    }

    private fun loadWebsite() {

        webView = WebView(this)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.allowFileAccess = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

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
        layout.setBackgroundColor(Color.WHITE)

        val title = TextView(this)
        title.text = "اتصال برقرار نشد"
        title.textSize = 24f
        title.setTypeface(null, Typeface.BOLD)
        title.gravity = Gravity.CENTER

        val message = TextView(this)
        message.text = "لطفاً اتصال اینترنت دستگاه را بررسی کنید."
        message.textSize = 16f
        message.gravity = Gravity.CENTER
        message.setPadding(0, 20, 0, 30)

        val retry = Button(this)
        retry.text = "تلاش مجدد"

        retry.setOnClickListener {
            showLoading()
        }

        layout.addView(title)
        layout.addView(message)
        layout.addView(retry)

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
