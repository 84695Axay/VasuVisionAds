# Load Ads

#
Load InterstitialAd

        loadInterstitialAd("ID",isPro = false)
        
#
Load RewardVideoAd

        loadRewardVideoAd("ID",isPro = false)
        
#
Load and show NativeAdvance

        loadNativeAdvanceBig("ID",
            findViewById(R.id.fl_adplaceholder),
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }, isPro = false)
        
 #
 Load and Show Adaptive Banner
 
        loadBannerAd("ID", findViewById(R.id.flBanenr),
            AdaptiveBanner.BannerSize.LARGE_BANNER, 
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        }, isPro = false)
        
        # For Custom Height
        
        loadBannerAd("ID", findViewById(R.id.flBanenr),
            AdaptiveBanner.BannerSize.CUSTOM, 75,
            onAdLoaded = {

            },
            onAdFailedToLoad = { errorMsg: String?, errorCode: String? ->

            }
        }, isPro = false)
        
# Show Ads

#
Show Interstitial

        showInterstitialAd(
            onAdShowed = {

            },
            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->

            },
            onAdDismissed = {

            }, isPro = false
        )
            
#
Show RewardVideo Ads

        showRewardedAd(
            onAdDismissed = {
            },
            onAdShowed = {
            },
            onAdFailedToShow = { errorMsg: String?, errorCode: String? ->
            },
            onUserEarned = {
            }, isPro = false
        )
