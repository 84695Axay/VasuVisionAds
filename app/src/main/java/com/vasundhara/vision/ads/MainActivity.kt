package com.vasundhara.vision.ads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vasundhara.vision.mylibrary.AdaptiveBanner
import com.vasundhara.vision.mylibrary.AdaptiveBanner.loadBannerAd
import com.vasundhara.vision.mylibrary.InterstitialAdsHelper
import com.vasundhara.vision.mylibrary.InterstitialAdsHelper.loadInterstitialAd
import com.vasundhara.vision.mylibrary.NativeAdvanced.loadNativeAdvanceBig
import com.vasundhara.vision.mylibrary.RewardVideoAds
import com.vasundhara.vision.mylibrary.RewardVideoAds.loadRewardVideoAd

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadRewardVideoAd("ca-app-pub-3940256099942544/5224354917")
        loadInterstitialAd("ca-app-pub-3940256099942544/1033173712")
        loadNativeAdvanceBig("ca-app-pub-3940256099942544/1044960115",
            findViewById(R.id.fl_adplaceholder),
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        loadBannerAd("ca-app-pub-3940256099942544/6300978111", findViewById(R.id.flBanenr),
            AdaptiveBanner.BannerSize.CUSTOM, 250,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
    }

    override fun onBackPressed() {
        finish()
//        InterstitialAdsHelper.showInterstitialAd(
//            this,
//            onAdShowed = {
//
//            },
//            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
//
//            },
//            onPro = {
//
//            },
//            onAdDismissed = {
//
//            }, isPro = false
//        )

//        RewardVideoAds.showRewardedAd(
//            this,
//            onAdDismissed = {
//            },
//            onAdShowed = {
//            },
//            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
//            },
//            onUserEarned = {
//            },
//        )
    }
}