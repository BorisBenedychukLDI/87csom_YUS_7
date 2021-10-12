package com.kosmos.mos.lot.com.WebActivity87csom

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.core.content.FileProvider
import com.kosmos.mos.lot.com.Utils87csom.filePathCallBack87csom
import com.kosmos.mos.lot.com.Utils87csom.uriView87csom
import kotlinx.coroutines.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class WebPresenter_Impl87csom(private val webMethods87csom: WebContract87csom.WebMethods87csom) : WebContract87csom.WebPresenter87csom {

    private var job87csom: Job? = null

    override fun setupWebView87csom() {
        webMethods87csom.webView87csom().run {
            settings.run {
                loadWithOverviewMode = true
                displayZoomControls = false
                useWideViewPort = true
                javaScriptEnabled = true
                loadsImagesAutomatically = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                builtInZoomControls = true
                displayZoomControls = false
                domStorageEnabled = true
                mediaPlaybackRequiresUserGesture = false
            }
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        }
    }

    override fun setupWebClient87csom() {
        webMethods87csom.webView87csom().webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view87csom: WebView?,
                request87csom: WebResourceRequest?
            ): Boolean {
                val modifiedLinks87csom = listOf("mailto:", "tel:")
                return when {
                    modifiedLinks87csom.filter {
                        request87csom
                            ?.url.toString().startsWith(it)
                    }.isNotEmpty() -> {
                        view87csom?.context?.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                request87csom?.url
                            )
                        )
                        true
                    }
                    else -> false
                }
            }

            override fun onPageFinished(view87csom: WebView?, url87csom: String?) {
                webMethods87csom.sharedPreferences87csom().edit().putString("Last_Page_87csom", url87csom?: return).apply()
                super.onPageFinished(view87csom, url87csom)
            }

            override fun onReceivedSslError(
                view87csom: WebView?,
                handler87csom: SslErrorHandler?,
                error87csom: SslError?
            ) {
                handler87csom?.proceed()
                super.onReceivedSslError(view87csom, handler87csom, error87csom)
            }
        }
    }

    override fun setupWebChromeClient87csom() {
        webMethods87csom.webView87csom().webChromeClient = object : WebChromeClient () {
            override fun onShowFileChooser(
                webView87csom: WebView?,
                filePathCallback87csom: ValueCallback<Array<Uri>>?,
                fileChooserParams87csom: FileChooserParams?
            ): Boolean {
                webMethods87csom.checkPermissions87csom()
                filePathCallBack87csom = filePathCallback87csom
                val captureIntent87csom = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (captureIntent87csom.resolveActivity(webMethods87csom.context87csom().packageManager) != null) {
                    val providedFile87csom = createTempFile87csom()
                    uriView87csom = FileProvider.getUriForFile(
                        webMethods87csom.context87csom(),
                        "${webMethods87csom.context87csom().packageName}.provider",
                        providedFile87csom
                    )
                    captureIntent87csom.run {
                        putExtra(MediaStore.EXTRA_OUTPUT, uriView87csom)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    val actionIntent87csom = Intent(Intent.ACTION_GET_CONTENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }
                    val intentChooser87csom = Intent(Intent.ACTION_CHOOSER).apply {
                        putExtra(Intent.EXTRA_INTENT, captureIntent87csom)
                        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntent87csom))
                    }
                    webMethods87csom.startActivityForResult87csom(intentChooser87csom)
                    return true

                }
                return super.onShowFileChooser(webView87csom, filePathCallback87csom, fileChooserParams87csom)
            }
        }
    }

    override fun loadTheOne87csom() {
        webMethods87csom.sharedPreferences87csom().getString("Last_Page_87csom",null)?.let {
                lastpage87csom ->
            webMethods87csom.webView87csom().loadUrl(lastpage87csom)
            return
        }
        webMethods87csom.webView87csom().loadUrl(webMethods87csom.intentString87csom() ?: return)
        Log.d ("TEST_URL", webMethods87csom.intentString87csom() ?: return)
    }

    override fun internetChecker87csom() {
        job87csom = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                delay(500)
                isNetworkIsPresent87csom().run {
                    if (!this) {
                        webMethods87csom.handleNetworkMissing87csom()
                        cancel()
                    }
                }
            }
        }
    }

    override fun isNetworkIsPresent87csom(): Boolean {
        val connectivityManager87csom = webMethods87csom.context87csom().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities87csom = connectivityManager87csom.getNetworkCapabilities(connectivityManager87csom.activeNetwork) ?: return false
            return networkCapabilities87csom.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            for (network87csom in connectivityManager87csom.allNetworks) {
                connectivityManager87csom.getNetworkInfo(network87csom)?.let {
                    if (it.isConnected) return true
                }
            }
            return false
        }
    }

    override fun stopChecking87csom() {
        job87csom?.cancel()
    }

    private fun createTempFile87csom(): File {
        val date87csom = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        val fileDir87csom = webMethods87csom.context87csom().getExternalFilesDir(
            Environment.DIRECTORY_PICTURES)
        return File.createTempFile("TMP${date87csom}_87csom", ".jpg", fileDir87csom)
    }
}