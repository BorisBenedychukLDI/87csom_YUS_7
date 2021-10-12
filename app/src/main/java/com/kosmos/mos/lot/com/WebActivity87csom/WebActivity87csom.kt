package com.kosmos.mos.lot.com.WebActivity87csom

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.kosmos.mos.lot.com.R
import com.kosmos.mos.lot.com.Utils87csom.filePathCallBack87csom
import com.kosmos.mos.lot.com.Utils87csom.uriView87csom
import com.kosmos.mos.lot.com.databinding.ActivityWebActivity87csomBinding
import kotlinx.coroutines.launch

class WebActivity87csom : AppCompatActivity(), WebContract87csom.WebMethods87csom {

    private lateinit var binding87csom: ActivityWebActivity87csomBinding
    private lateinit var webViewPresenter87csom: WebContract87csom.WebPresenter87csom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding87csom = ActivityWebActivity87csomBinding.inflate(layoutInflater)
        setContentView(binding87csom.root)
        webViewPresenter87csom = WebPresenter_Impl87csom(this)
        webViewPresenter87csom.setupWebView87csom()
        webViewPresenter87csom.setupWebClient87csom()
        webViewPresenter87csom.setupWebChromeClient87csom()
        webViewPresenter87csom.loadTheOne87csom()
        binding87csom.srl87csom.setOnRefreshListener {
            binding87csom.wv87csom.loadUrl(
                binding87csom.wv87csom.url ?: return@setOnRefreshListener
            )
            binding87csom.srl87csom.isRefreshing = false
        }
    }

    override fun onResume() {
        webViewPresenter87csom.internetChecker87csom()
        super.onResume()
    }

    override fun onPause() {
        webViewPresenter87csom.stopChecking87csom()
        super.onPause()
    }

    override fun webView87csom(): WebView = binding87csom.wv87csom

    override fun sharedPreferences87csom(): SharedPreferences =
        getSharedPreferences("SP_87csom", MODE_PRIVATE)


    override fun checkPermissions87csom() {
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }
            }).check()
    }

    override fun context87csom(): Context = this


    override fun startActivityForResult87csom(intent87csom: Intent) {
        startActivityForResult(intent87csom, 0)
    }

    override fun intentString87csom(): String? = intent.getStringExtra("webURL87csom")


    override fun handleNetworkMissing87csom() {
        lifecycleScope.launch {
            binding87csom.srl87csom.animate().alpha(0f).run {
                duration = 500
            }
            binding87csom.wv87csom.animate().alpha(0f).run {
                duration = 500
            }
            binding87csom.tvNoConnectionLabel87csom.animate().alpha(1f).run {
                startDelay = 500
                duration = 500
            }
            binding87csom.bInternet87csom.animate().alpha(1f).run {
                startDelay = 500
                duration = 500
            }
            binding87csom.lanimationInternet87csom.animate().alpha(1f).run {
                startDelay = 500
                duration = 500
            }
            ValueAnimator.ofFloat(0f, 1f).run {
                duration = 1000
                addUpdateListener {
                    binding87csom.lanimationInternet87csom.progress = it.animatedValue as Float
                }
                start()
            }
            binding87csom.bInternet87csom.setOnClickListener {
                if (webViewPresenter87csom.isNetworkIsPresent87csom()) {
                    ValueAnimator.ofArgb(
                        resources.getColor(R.color.theme_87csom),
                        resources.getColor(R.color.theme_green_87csom)
                    ).run {
                        duration = 500
                        repeatCount = 1
                        repeatMode = ValueAnimator.REVERSE
                        setEvaluator(ArgbEvaluator())
                        addUpdateListener {
                            binding87csom.root.setBackgroundColor(it.animatedValue as Int)
                        }
                        doOnEnd {
                            binding87csom.srl87csom.animate().alpha(1f).run {
                                startDelay = 300
                                duration = 500
                            }
                            binding87csom.wv87csom.animate().alpha(1f).run {
                                startDelay = 300
                                duration = 500
                            }
                            binding87csom.tvNoConnectionLabel87csom.animate().alpha(0f).run {
                                duration = 500
                            }
                            binding87csom.bInternet87csom.animate().alpha(0f).run {
                                duration = 500
                            }
                            binding87csom.lanimationInternet87csom.animate().alpha(0f).run {
                                duration = 500
                            }
                            webViewPresenter87csom.internetChecker87csom()
                        }
                        start()
                    }
                    ValueAnimator.ofFloat(1f, 0f).run {
                        duration = 1000
                        addUpdateListener {
                            binding87csom.lanimationInternet87csom.progress =
                                it.animatedValue as Float
                        }
                        start()
                    }
                } else {
                    ValueAnimator.ofArgb(
                        resources.getColor(R.color.theme_87csom),
                        resources.getColor(R.color.theme_red_87csom)
                    ).run {
                        duration = 500
                        repeatCount = 1
                        repeatMode = ValueAnimator.REVERSE
                        setEvaluator(ArgbEvaluator())
                        addUpdateListener {
                            binding87csom.root.setBackgroundColor(it.animatedValue as Int)
                        }
                        start()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        return if (binding87csom.wv87csom.canGoBack())
            binding87csom.wv87csom.goBack() else
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode87csom: Int, resultCode87csom: Int, data87csom: Intent?) {
        if (filePathCallBack87csom != null && requestCode87csom == 0) {
            val uriResult87csom =
                if (data87csom == null || resultCode87csom != RESULT_OK) null else data87csom.data
            if (uriResult87csom != null) {
                filePathCallBack87csom?.onReceiveValue(arrayOf(uriResult87csom))
            } else {
                filePathCallBack87csom?.onReceiveValue(arrayOf(uriView87csom))
            }
            filePathCallBack87csom = null
        }
        super.onActivityResult(requestCode87csom, resultCode87csom, data87csom)
    }
}