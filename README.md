# Load Ads

#
Load InterstitialAd

        loadInterstitialAd("ca-app-pub-3940256099942544/1033173712")
        
#
Load RewardVideoAd

        loadRewardVideoAd("ca-app-pub-3940256099942544/5224354917")
        
#
Load and show NativeAdvance

        loadNativeAdvanceBig("ca-app-pub-3940256099942544/1044960115",
            findViewById(R.id.nativeFrameLayout),
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        
 #
 Load and Show Adaptive Banner
 
        loadBannerAd("ca-app-pub-3940256099942544/6300978111", findViewById(R.id.bannerFrameLayout),
            AdaptiveBanner.BannerSize.CUSTOM, 250,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        
        # For Custom Height
        
        loadBannerAd("ca-app-pub-3940256099942544/6300978111", findViewById(R.id.bannerFrameLayout),
            AdaptiveBanner.BannerSize.CUSTOM, 250,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        
# Show Ads

#
Show Interstitial

        InterstitialAdsHelper.showInterstitialAd(
            this,
            onAdShowed = {

            },
            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->

            },
            onAdDismissed = {

            })
            
#
Show RewardVideo Ads

        RewardVideoAds.showRewardedAd(
            this,
            onAdDismissed = {
            },
            onAdShowed = {
            },
            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
            },
            onUserEarned = {
            },
        )
