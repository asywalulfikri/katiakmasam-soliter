package de.tobiasbielefeld.solitaire.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;

import de.tobiasbielefeld.solitaire.R;


/**
 * Created by asywalulfikri on 10/4/16.
 */

public class InterestialLogout extends AppCompatActivity{

    InterstitialAd mInterstitialAd;
    private static final int WAIT_TIME = 5000;

    private Timer waitTimer;
    private boolean interstitialCanceled = false;
    String IKLAN_LOGOUT = "ca-app-pub-4914903732265878/7813224549";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interestail);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(IKLAN_LOGOUT);
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (!interstitialCanceled) {
                    waitTimer.cancel();
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                startSplashActivity();
            }

        });

        waitTimer = new Timer();
        waitTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                interstitialCanceled = true;
                InterestialLogout.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        startSplashActivity();

                    }
                });
            }
        }, WAIT_TIME);

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }

    @Override
    protected void onPause() {
        waitTimer.cancel();
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else if (interstitialCanceled) {
            startSplashActivity();
        }
    }

    private void startSplashActivity() {
      /*  Intent intent = new Intent(this, SplashScreen.class);
        startActivity(intent);*/
        moveTaskToBack(true);
        finish();
    }
}
