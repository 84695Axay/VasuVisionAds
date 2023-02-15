package com.vasundhara.vision.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vasundhara.vision.mylibrary.AdaptiveBanner
import com.vasundhara.vision.mylibrary.AdaptiveBanner.loadBannerAd
import com.vasundhara.vision.mylibrary.InterstitialAdsHelper
import com.vasundhara.vision.mylibrary.InterstitialAdsHelper.loadInterstitialAd
import com.vasundhara.vision.mylibrary.InterstitialAdsHelper.showInterstitialAd
import com.vasundhara.vision.mylibrary.NativeAdvanced.loadNativeAdvanceBig
import com.vasundhara.vision.mylibrary.RewardVideoAds
import com.vasundhara.vision.mylibrary.RewardVideoAds.loadRewardVideoAd
import com.vasundhara.vision.mylibrary.RewardVideoAds.showRewardedAd

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadRewardVideoAd("ca-app-pub-3940256099942544/5224354917",isPro = false)
        loadInterstitialAd("ca-app-pub-3940256099942544/1033173712",isPro = false)
        loadNativeAdvanceBig("ca-app-pub-3940256099942544/1044960115",
            findViewById(R.id.fl_adplaceholder),
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }, isPro = false)
        loadBannerAd("ca-app-pub-3940256099942544/6300978111", findViewById(R.id.flBanenr),
            AdaptiveBanner.BannerSize.CUSTOM,75,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }, isPro = false
        )
    }
    override fun onBackPressed() {
//        showInterstitialAd(
//            onAdShowed = {
//
//            },
//            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
//
//            },
//            onAdDismissed = {
//
//            }, isPro = false
//        )

//        showRewardedAd(
//            onAdDismissed = {
//            },
//            onAdShowed = {
//            },
//            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
//            },
//            onUserEarned = {
//            }, isPro = false
//        )
    }
}