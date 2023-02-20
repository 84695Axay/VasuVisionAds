package com.vasundhara.vision.mylibrary

import android.app.Activity
import android.app.ProgressDialog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.*

object RewardVideoAds {
    val TAG = RewardVideoAds::class.java.simpleName
    var rewardedAds: RewardedAd? = null
    var isGoingToShow: Boolean = false
    var adsId: String = "Ads ID"
    var isGoingToLoad: Boolean = false

    fun Activity.loadRewardVideoAd(adsIDs: String, isPro: Boolean? = false) {
        if (isPro!!) {
            return
        }
        if (isGoingToLoad) {
            return
        }
        adsId = adsIDs
        isGoingToLoad = true
        val adLoadCallback: RewardedAdLoadCallback = object : RewardedAdLoadCallback() {
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                super.onAdLoaded(rewardedAd)
                isGoingToLoad = false
                rewardedAds = rewardedAd
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                isGoingToLoad = false
                rewardedAds = null
            }
        }
        RewardedAd.load(this, adsId, AdRequest.Builder().build(), adLoadCallback)
    }

    fun Activity.showRewardedAd(
        onAdClicked: () -> Unit,
        onAdShowed: () -> Unit,
        onAdDismissed: () -> Unit,
        onUserEarned: () -> Unit,
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
        try {
            if (rewardedAds != null) {
                rewardedAds!!.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            rewardedAds = null
                            isGoingToShow = false
                            loadRewardVideoAd(adsId)
                            onAdDismissed()
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            rewardedAds = null
                            isGoingToShow = false
                            onAdFailedToShow("Ad not loaded", "Ad not loaded")
                            loadRewardVideoAd(adsId)
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            onAdClicked()
                        }
                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            onAdShowed()
                        }

                    }
                rewardedAds!!.show(this@showRewardedAd, OnUserEarnedRewardListener {
                    isGoingToShow = false
                    onUserEarned()
                })
            } else {
                isGoingToShow = false
                loadRewardVideoAd(adsId)
                onAdFailedToShow("Ad not loaded", "Ad not loaded")
            }
        } catch (e: Exception) {
            rewardedAds = null
            isGoingToShow = false
            onAdFailedToShow("Ad not loaded", "Ad not loaded")
            e.printStackTrace()
            loadRewardVideoAd(adsId)
        }

    }
}