package com.vasundhara.vision.mylibrary

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.*

object InterstitialAdsHelper {
    val TAG =
        "InterstitialAdsHelper"
    var interstitialAd1: InterstitialAd? = null
    var isLoadedFail: Boolean = false
    var isGoingToLoad: Boolean = false
    var isGoingToShow: Boolean = false
    var adsId: String = "Ads ID"
    private fun Activity.isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    fun Activity.loadInterstitialAd(adsIds: String, isPro: Boolean? = false) {
        if (isPro!!) {
            return
        }
        adsId = adsIds
        loadInterstitial()
    }

    private fun Activity.loadInterstitial() {
        if (interstitialAd1 == null || isNetworkAvailable()) {
            loadInterstitialAdmob()
        }
    }

    private fun Activity.loadInterstitialAdmob() {
        if (isGoingToLoad) {
            Log.d(TAG, "going to load : loadInterstitialAdmob already loaded")
            return
        }
        isGoingToLoad = true
        InterstitialAd.load(
            this,
            adsId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(
                    adError: LoadAdError
                ) {
                    interstitialAd1 = null
                    isGoingToLoad = false
                    try {
                        if (!isLoadedFail && isNetworkAvailable()) {
                            isLoadedFail = true
                            loadInterstitialAdmob()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    isGoingToLoad = false
                    interstitialAd1 = interstitialAd
                    isLoadedFail = false
                }
            })
    }

    fun Activity.showInterstitialAd(
        onAdClicked: () -> Unit,
        onAdShowed: () -> Unit,
        onAdDismissed: () -> Unit,
        onAdFailedToShow: (errorMsg: String?, errorCode: String?) -> Unit,
        isPro: Boolean? = false
    ) {
        if (isPro!!) {
            onAdFailedToShow("PRO User","PRO User")
            return
        }
        if (isGoingToShow) {
            return
        }
        isGoingToShow = true
        val progress = ProgressDialog(this)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                progress.setMessage("Loading ad...")
                progress.show()
            }
            withContext(Dispatchers.Main) {
                delay(1000)
                try {
                    progress.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    if (interstitialAd1 != null) {
                        interstitialAd1!!.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        onAdDismissed()
                                    }, 50)
                                    interstitialAd1 = null
                                    loadInterstitial()
                                    isGoingToShow = false
                                }

                                override fun onAdClicked() {
                                    super.onAdClicked()
                                    onAdClicked()
                                }
                                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                    super.onAdFailedToShowFullScreenContent(p0)
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        onAdFailedToShow(p0.message, p0.code.toString())
                                    }, 50)
                                    interstitialAd1 = null
                                    loadInterstitial()
                                    isGoingToShow = false
                                }

                                override fun onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent()
                                    onAdShowed()
                                    isGoingToShow = false
                                }
                            }
                        interstitialAd1!!.show(this@showInterstitialAd)
                    } else {
                        isGoingToShow = false
                        loadInterstitialAd(adsId)
                        onAdFailedToShow("Ad not loaded", "Ad not loaded")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    isGoingToShow = false
                    onAdFailedToShow("Exception: ${e.message}", "Exception: ${e.message}")
                }
            }
        }
    }
}
