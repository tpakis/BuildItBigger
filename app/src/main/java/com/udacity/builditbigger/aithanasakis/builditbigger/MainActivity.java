package com.udacity.builditbigger.aithanasakis.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.builditbigger.aithanasakis.builditbigger.backend.myApi.MyApi;
import com.udacity.builditbigger.aithanasakis.jokesactivity.JokesActivity;
import com.udacity.builditbigger.aithanasakis.jokesmith.JokeSmith;

import java.io.IOException;

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
    //there should be an id in the project for butterknife to work so included an empty layout...
    @Nullable
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
        //applicationid is different than package name and is overriding the application id
        //in the manifest when using gradle
        if (BuildConfig.APPLICATION_ID.endsWith(".free")) {
            //: If you haven't created an AdMob account and registered an app yet, now's a great time to do so.
            // If you're just looking to experiment with the SDK in a Hello World app, though, you can use
            // this App ID to initialize the SDK: ca-app-pub-3940256099942544~3347511713.
            MobileAds.initialize(this,appID );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    Handler handler = new Handler();
                    final Runnable r = new Runnable()
                    {
                        public void run()
                        {
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

                @Override
                public void onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the user is about to return
                    // to the app after tapping on an ad.
                }
            });
        }else {                 //paid app
             jokeTask.execute();
        }

    }

    public void showJoke(String joke) {
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(Constants.JOKE_INTENNT, joke);
        startActivity(intent);
        //   testing.setText(joke);
    }

    @Override
    public void onJokeResult(String joke) {
        progressBar.setVisibility(View.GONE);
        if (!joke.contains("java.net.")){
            showJoke(joke);
        }else{
            Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
        }

    }
}
