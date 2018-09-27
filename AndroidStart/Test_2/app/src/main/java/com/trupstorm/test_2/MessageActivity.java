package com.trupstorm.test_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MessageActivity extends AppCompatActivity {

    Button sendButton;
    Button cancelButton;
    EditText messageToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        sendButton = (Button) findViewById(R.id.button);
        cancelButton = (Button) findViewById(R.id.button2);
        messageToEdit = (EditText) findViewById(R.id.editTextOne);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageToEdit.getText().toString();
                Intent result = new Intent();
                result.putExtra("MESSAGE_DATA", message);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
}
