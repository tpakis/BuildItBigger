package com.udacity.builditbigger.aithanasakis.builditbigger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.builditbigger.aithanasakis.jokesmith.JokeSmith;

public class MainActivity extends AppCompatActivity {

    JokeSmith jokeSmith;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView testing = (TextView) findViewById(R.id.testing);
        testing.setText(jokeSmith.getJoke());
    }
}
