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

    fun showRewardedAd(
        context: Activity,
        onAdShowed: () -> Unit,
        onAdDismissed: () -> Unit,
        onUserEarned: () -> Unit,
        onAdFailedToShow: (errorMsg: String?, errorCode: String?) -> Unit
    ) {
        if (isGoingToShow) {
            return
        }
        isGoingToShow = true
        val progress = ProgressDialog(context)
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
                    if (rewardedAds != null) {
                        rewardedAds!!.fullScreenContentCallback =
                            object : FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent()
                                    rewardedAds = null
                                    isGoingToShow=false
                                    context.loadRewardVideoAd(adsId)
                                    onAdDismissed()
                                }

                                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                    super.onAdFailedToShowFullScreenContent(p0)
                                    rewardedAds = null
                                    isGoingToShow=false
                                    onAdFailedToShow("Ad not loaded", "Ad not loaded")
                                    context.loadRewardVideoAd(adsId)
                                }

                                override fun onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent()
                                    onAdShowed()
                                }

                            }
                        rewardedAds!!.show(context, OnUserEarnedRewardListener {
                            isGoingToShow=false
                            onUserEarned()
                        })
                    } else {
                        isGoingToShow=false
                        context.loadRewardVideoAd(adsId)
                        onAdFailedToShow("Ad not loaded", "Ad not loaded")
                    }
                } catch (e: Exception) {
                    rewardedAds = null
                    isGoingToShow=false
                    onAdFailedToShow("Ad not loaded", "Ad not loaded")
                    e.printStackTrace()
                    context.loadRewardVideoAd(adsId)
                }
            }
        }
    }
}