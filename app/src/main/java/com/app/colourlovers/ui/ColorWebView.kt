package com.app.colourlovers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.app.colourlovers.R
import com.app.colourlovers.databinding.ActivityColorWebViewBinding
import com.app.colourlovers.databinding.ActivityMainBinding

class ColorWebView : AppCompatActivity() {
    lateinit var mBinding: ActivityColorWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityColorWebViewBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        // val view = mBinding.root
        setContentView(mBinding.root)
        mBinding.webView.webViewClient = WebViewClient()

        mBinding.webView.loadUrl(intent.getStringExtra("URL").toString())

        mBinding.webView.settings.javaScriptEnabled = true

//        mBinding.webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (mBinding.webView.canGoBack())
            mBinding.webView.goBack()
        else
            super.onBackPressed()
    }
}