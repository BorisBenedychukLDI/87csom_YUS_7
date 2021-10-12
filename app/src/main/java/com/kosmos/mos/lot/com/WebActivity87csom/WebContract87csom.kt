package com.kosmos.mos.lot.com.WebActivity87csom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.webkit.WebView

interface WebContract87csom {

    interface WebMethods87csom {
        fun webView87csom(): WebView
        fun sharedPreferences87csom(): SharedPreferences
        fun checkPermissions87csom()
        fun context87csom(): Context
        fun startActivityForResult87csom(intent87csom: Intent)
        fun intentString87csom(): String?
        fun handleNetworkMissing87csom()
    }

    interface WebPresenter87csom {
        fun setupWebView87csom()
        fun setupWebClient87csom()
        fun setupWebChromeClient87csom()
        fun loadTheOne87csom ()
        fun internetChecker87csom ()
        fun isNetworkIsPresent87csom ():Boolean
        fun stopChecking87csom ()
    }
}