package com.trupstorm.test3;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
   Button firstBtn;
   TextView resultsTextView;
   ProgressBar progressBar;
   Integer count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultsTextView = (TextView) findViewById(R.id.textViewOne);
        firstBtn = (Button) findViewById(R.id.buttonOne);
        progressBar = (ProgressBar) findViewById(R.id.progressBarOne);

        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DelayTask().execute(10);
            }
        });
    }

    class DelayTask extends AsyncTask<Integer,Integer,Integer> {

        @Override
        protected void onPreExecute() {
            resultsTextView.setText("Uruchomienie zadania");

        }

        @Override
        protected void onPostExecute(Integer integer) {
            resultsTextView.setText("Zadanie wykonane");
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}