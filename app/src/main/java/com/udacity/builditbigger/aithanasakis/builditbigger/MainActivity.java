package com.udacity.builditbigger.aithanasakis.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.builditbigger.aithanasakis.builditbigger.backend.myApi.MyApi;
import com.udacity.builditbigger.aithanasakis.jokesactivity.JokesActivity;
import com.udacity.builditbigger.aithanasakis.jokesmith.JokeSmith;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    JokeSmith jokeSmith;
    @BindView(R.id.testing)
    TextView testing;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        jokeSmith = new JokeSmith();
        JokeTask jokeTask = new JokeTask();
        jokeTask.execute();
    }

    public void showJoke(String joke) {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, JokesActivity.class);
        intent.putExtra(Constants.JOKE_INTENNT, joke);
        startActivity(intent);
        //   testing.setText(joke);
    }

    public class JokeTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... voids) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.tellJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            showJoke(result);
        }

    }
}
