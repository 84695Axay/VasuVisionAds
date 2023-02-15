package com.vasundhara.vision.mylibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.ads.*

object AdaptiveBanner {
    private val TAG = AdaptiveBanner::class.java.simpleName
    var adsId: String = "Ads ID"

    private fun Activity.isOnline(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @SuppressLint("MissingPermission") val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                if (activeNetwork.isConnected) haveConnectedWifi = true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                if (activeNetwork.isConnected) haveConnectedMobile = true
            }
        }
        return haveConnectedWifi || haveConnectedMobile
    }

    private fun Activity.adSize(fAdContainer: FrameLayout): AdSize {
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = fAdContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }

    enum class BannerSize() {
        LEADERBOARD,
        FULL_BANNER,
        LARGE_BANNER,
        MEDIUM_RECTANGLE,
        WIDE_SKYSCRAPER,
        BANNER,
        CUSTOM
    }

    fun Activity.loadBannerAd(
        adsIds: String,
        frameLayout: FrameLayout,
        bannerSize: BannerSize? = null,
        height: Int? = null,
        onAdLoaded: () -> Unit?,
        onAdFailedToLoad: (errorMsg: String?, errorCode: String?) -> Unit?,
        isPro: Boolean?=false
    ) {
        if (isPro!!) {
            return
        }
        if (!isOnline()) {
            onAdFailedToLoad("Please Check Internet Connection", "Please Check Internet Connection")
            return
        }
        adsId = adsIds

        val adView: AdView = AdView(this)
        adView.removeAllViews()
        adView.adUnitId = adsId
        val adSize = adSize(frameLayout)
        when (bannerSize) {
            BannerSize.LEADERBOARD -> {
                adView.setAdSize(AdSize.LEADERBOARD)
            }
            BannerSize.FULL_BANNER -> {
                adView.setAdSize(AdSize.FULL_BANNER)
            }
            BannerSize.LARGE_BANNER -> {
                adView.setAdSize(AdSize.LARGE_BANNER)
            }
            BannerSize.MEDIUM_RECTANGLE -> {
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE)
            }
            BannerSize.WIDE_SKYSCRAPER -> {
                adView.setAdSize(AdSize.WIDE_SKYSCRAPER)
            }
            BannerSize.BANNER -> {
                adView.setAdSize(AdSize.BANNER)
            }
            BannerSize.CUSTOM -> {
                if (height == null) {
                    adView.setAdSize(adSize)
                } else {
                    adView.setAdSize(
                        AdSize.getInlineAdaptiveBannerAdSize(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            height
                        )
                    )
                }
            }
            else -> {
                adView.setAdSize(adSize)
            }
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                onAdFailedToLoad(
                    p0.message,
                    p0.code.toString()
                )
            }

            override fun onAdImpression() {
            }

            override fun onAdLoaded() {
                onAdLoaded()
            }

            override fun onAdOpened() {
            }
        }
        frameLayout.addView(adView)

    }
}