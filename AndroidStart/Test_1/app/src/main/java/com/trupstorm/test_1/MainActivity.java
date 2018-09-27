package com.trupstorm.test_1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open another activity in application context
        Button bt_one = (Button) findViewById(R.id.buttonOne);
        bt_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),SecondaryActivity.class);
                startIntent.putExtra("com.trupstorm.MESSAGE", "Greetings to the second activity");
                startActivity(startIntent);
            }
        });

        //open activity in global context (search app in device)
        //map example
        Button bt_two = (Button) findViewById(R.id.buttonTwo);
        bt_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //"?z=8" == adding to uri zoom condition (1 == whole map, 2 == half of 1, 3 == half of 2 ...)
                String uriGeo = "geo: 37.422, -122.084?z=8";
                Uri geo = Uri.parse(uriGeo);
                Intent geoMan = new Intent(Intent.ACTION_VIEW, geo);
                startActivity(geoMan);
            }
        });

        //open activity in global context (search app in device)
        //web page example
        Button bt_three = (Button) findViewById(R.id.buttonThree);
        bt_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wwwUrl = "https://fedoramagazine.org/getting-started-i3-window-manager/";
                Uri web = Uri.parse(wwwUrl);
                Intent webMan = new Intent(Intent.ACTION_VIEW, web);
                startActivity(webMan);
            }
        });

    }
}
