package com.trupstorm.test_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        //whay without checking a intent
        /*Intent intent = getIntent();
        String message = intent.getStringExtra("com.trupstorm.MESSAGE");
        TextView viewMessage = (TextView) findViewById(R.id.textViewOne);
        viewMessage.setText(message);*/

        //If i want my activity to take care tasks send from the other app on device
        //+ plain intent
        String msg = "No additional data to send";
        Intent it_two = getIntent();
        if(it_two != null){
            //plain
            if(it_two.hasExtra("com.trupstorm.MESSAGE")){
                msg = it_two.getStringExtra("com.trupstorm.MESSAGE");
            } else if (it_two.hasExtra(Intent.EXTRA_TEXT)){
                //activity to take care
                msg = it_two.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        TextView viewMessage = (TextView) findViewById(R.id.textViewOne);
        viewMessage.setText(msg);




        //dont need for that example
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
