package com.vasundhara.vision.mylibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

object NativeAdvanced {
    var unNativeAd: NativeAd? = null
    private val TAG = NativeAdvanced::class.java.simpleName
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

    fun Activity.loadNativeAdvanceBig(
        adsIds: String, fAdContainer: FrameLayout, isPro: Boolean?=false,
        onAdClicked: () -> Unit,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (errorMsg: String?, errorCode: String?) -> Unit,
    ) {
        if (isPro!!){
            onAdFailedToLoad("PRO User","PRO User")
            return
        }
        adsId = adsIds
        val adView = layoutInflater.inflate(R.layout.layout_native_advance_new, null) as NativeAdView
        if (!isOnline()) {
            onAdFailedToLoad("Please Check Internet Connection","Please Check Internet Connection")
            return
        }
        if (unNativeAd == null) {
            val nativeAdOptions =
                NativeAdOptions.Builder().setMediaAspectRatio(MediaAspectRatio.LANDSCAPE).build()
            val builder = AdLoader.Builder(this, adsId).withNativeAdOptions(nativeAdOptions)
                .forNativeAd { nativeAd ->
                    populateNativeAdView(nativeAd, adView)
                    fAdContainer.removeAllViews()
                    fAdContainer.addView(adView)
                }

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    onAdFailedToLoad(error.message,error.code.toString())
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    onAdLoaded()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    unNativeAd = null
                    onAdClicked()
                    try {
                        loadNativeAdvanceBig(adsId,fAdContainer,isPro,onAdClicked, onAdLoaded,onAdFailedToLoad)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        } else {
            populateNativeAdView(unNativeAd!!, adView)
            fAdContainer.removeAllViews()
            fAdContainer.addView(adView)
            onAdLoaded()
        }
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        unNativeAd = nativeAd
        adView.mediaView = adView.findViewById(R.id.ad_media)
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        (adView.headlineView as TextView).text = nativeAd.headline
        if (nativeAd.mediaContent != null && adView.mediaView != null) {
            adView.mediaView!!.mediaContent = nativeAd.mediaContent!!
        }
        if (nativeAd.body == null && adView.bodyView != null) {
            adView.bodyView!!.visibility = View.GONE
        } else if (adView.bodyView != null) {
            adView.bodyView!!.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null && adView.callToActionView != null) {
            adView.callToActionView!!.visibility = View.INVISIBLE
        } else if (adView.callToActionView != null) {
            adView.callToActionView!!.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null && adView.iconView != null) {
            adView.iconView!!.visibility = View.GONE
        } else if (adView.iconView != null) {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon!!.drawable
            )
            adView.iconView!!.visibility = View.VISIBLE
        }
        if (adView.priceView != null) {
            adView.priceView!!.visibility = View.GONE
        }
        if (adView.storeView != null) {
            adView.storeView!!.visibility = View.GONE
        }

        if (nativeAd.starRating == null && adView.starRatingView != null) {
            adView.starRatingView!!.visibility = View.INVISIBLE
        } else if (adView.starRatingView != null) {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView!!.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null && adView.advertiserView != null) {
            adView.advertiserView!!.visibility = View.INVISIBLE
        } else if (adView.advertiserView != null) {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView!!.visibility = View.VISIBLE
        }
        adView.setNativeAd(nativeAd)

    }
}
