package com.udacity.builditbigger.aithanasakis.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.udacity.builditbigger.aithanasakis.jokesactivity.JokesActivity;
import com.udacity.builditbigger.aithanasakis.jokesmith.JokeSmith;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements JokeTask.JokeListener {

    JokeSmith jokeSmith;
    @BindString(R.string.error_message)
    String errorMessage;
    @BindString(R.string.appID)
    String appID;
    @BindView(R.id.testing)
    TextView testing;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.adView)
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        jokeSmith = new JokeSmith();
        final JokeTask jokeTask = new JokeTask(MainActivity.this);

        //: If you haven't created an AdMob account and registered an app yet, now's a great time to do so.
        // If you're just looking to experiment with the SDK in a Hello World app, though, you can use
        // this App ID to initialize the SDK: ca-app-pub-3940256099942544~3347511713.
        MobileAds.initialize(this, appID);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading. I wanted the ad to stay visible
                //for some time before I open the joke activity
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        jokeTask.execute();
                    }
                };
                handler.postDelayed(r, 1500);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                jokeTask.execute();
            }

        });


    }

    public void showJoke(String joke) {
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(JokesActivity.JOKE_INTENNT, joke);
        startActivity(intent);
        //   testing.setText(joke);
    }

    @Override
    public void onJokeResult(String joke) {
        progressBar.setVisibility(View.GONE);
        if (!joke.contains("java.net.")) {
            showJoke(joke);
        } else {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }

    }
}
