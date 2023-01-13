# Load Ads

#
Load InterstitialAd

        loadInterstitialAd("ID")
        
#
Load RewardVideoAd

        loadRewardVideoAd("ID")
        
#
Load and show NativeAdvance

        loadNativeAdvanceBig("ID",
            findViewById(R.id.nativeFrameLayout),
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        
 #
 Load and Show Adaptive Banner
 
        loadBannerAd("ID", findViewById(R.id.bannerFrameLayout),
            AdaptiveBanner.BannerSize.CUSTOM, 250,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        )
        
        # For Custom Height
        
        loadBannerAd("ID", findViewById(R.id.bannerFrameLayout),
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
