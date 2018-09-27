package com.trupstorm.test_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static int MESSAGE_REQUEST_CODE = 0;
    TextView textView;
    Button button;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("saved_message", textView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.viewOne);
        button = (Button) findViewById(R.id.buttonOne);

        if(savedInstanceState != null){
            String savedMessage = savedInstanceState.getString("saved_message");
            textView.setText(savedMessage);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getResult = new Intent(getApplicationContext(), MessageActivity.class);
                startActivityForResult(getResult, MESSAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == MESSAGE_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                String message = data.getStringExtra("MESSAGE_DATA");
                textView.setText(message);
            }
        }
    }
}
