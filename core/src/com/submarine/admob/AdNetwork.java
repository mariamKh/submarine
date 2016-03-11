package com.submarine.admob;

/**
 * Created by sargis on 1/30/15.
 */
public interface AdNetwork {
    void showBanner();

    void hideBanner();

    void showInterstitial();

    void setInterstitialAdUnitId(String interstitialAdUnitId);

    void setAdViewUnitId(String adViewUnitId);

    void setInterstitialAdListener(InterstitialAdListener adListener);

    void requestNewInterstitial();
}