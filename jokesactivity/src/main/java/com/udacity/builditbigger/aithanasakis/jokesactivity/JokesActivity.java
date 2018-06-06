package com.udacity.builditbigger.aithanasakis.jokesactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {
    public static String JOKE_INTENNT = "JOKE_INTENNT";
    String joke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jokes);
        if (getIntent().getExtras()!=null && getIntent().hasExtra(JOKE_INTENNT)) {
            joke = getIntent().getExtras().getString(JOKE_INTENNT);
        }
        TextView testing = (TextView) findViewById(R.id.testing);
        testing.setText(joke);
    }
}