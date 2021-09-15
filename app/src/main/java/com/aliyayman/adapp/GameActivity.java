package com.aliyayman.adapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class GameActivity extends AppCompatActivity {
    private TextView textViewPuan;
    private Button buttonPuan,buttonNext;
    private AdView banner;
    private InterstitialAd myinterstitialAd;
    private int puan=10;
    private RewardedAd rewardedAd;
    private RewardedAdLoadCallback yuklemeListener;
    private RewardedAdCallback calismaListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textViewPuan=findViewById(R.id.textViewPuan);
        buttonNext=findViewById(R.id.buttonNext);
        buttonPuan=findViewById(R.id.buttonPuan);
        banner=findViewById(R.id.banner);

        buttonPuan.setVisibility(View.INVISIBLE);

        MobileAds.initialize(GameActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        bannerLoad();
        interstitialAdLoad();
        rewardLoad();


        buttonPuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rewardedAd.isLoaded()){
                    rewardedAd.show(GameActivity.this,calismaListener);
                }
            }
        });

        calismaListener=new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                puan+=20;
                textViewPuan.setText("TOPLAM PUAN:30");
                buttonPuan.setVisibility(View.INVISIBLE);
            }
        };



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(puan>=30){

                    if(myinterstitialAd.isLoaded()){
                        myinterstitialAd.show();
                    }
                }else{
                    Toast.makeText(GameActivity.this,"reklam izleyerek puanınınızı artırabilirsiniz.",Toast.LENGTH_SHORT).show();
                    buttonPuan.setVisibility(View.VISIBLE);
                }


            }
        });

        myinterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                startActivity(new Intent(GameActivity.this,NextStageActivity.class));
                finish();
            }

        });


    }

    public void bannerLoad(){

        banner.loadAd(new AdRequest.Builder().build());
    }

    public void interstitialAdLoad(){

        myinterstitialAd=new InterstitialAd(GameActivity.this);
        myinterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        myinterstitialAd.loadAd(new AdRequest.Builder().build());


    }
    public void rewardLoad(){
        rewardedAd=new RewardedAd(GameActivity.this,"ca-app-pub-3940256099942544/5224354917");
        yuklemeListener=new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                Log.e("yukleme listener","çalıştı");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(),yuklemeListener);

    }
}