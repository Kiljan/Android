package com.honestman.piotrk.notes;

import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends AppCompatActivity {
    
    /*create few important objects*/
    private DBHelper mydb ;
    TextView name ;
    TextView note;
    /*our note id must be update too*/
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        
        /*finding layouts*/
        name = (TextView) findViewById(R.id.editTextName);
        note = (TextView) findViewById(R.id.editTextNote);

        /*DBHelper works on class DisplayContact content*/
        mydb = new DBHelper(this);
        
        /*Receive data from MainActivity; for remainder that date is note id which we increment by one elier*/
        Intent in = getIntent();
        Bundle extras = in.getExtras();

        /*what we may do when a id is passed */
        if(extras !=null)
        {
            /*create integer value to hold passed id*/
            int Value = extras.getInt("id");

            /*when i open a note which is already exist...*/
            if(Value > 0)
            {
                /*cursor provides random read-write access to the result set returned by a database query;
                in other words getting a data from database where id is equal Value*/
                Cursor rs = mydb.getData(Value);
                
                id_To_Update = Value;
                
                /*I make sure that the cursor is on the first row*/
                rs.moveToFirst();

                /*getting a columns which i want modify in notes database
                now they are hold in that variables*/
                String nam = rs.getString(rs.getColumnIndex(DBHelper.NOTES_COLUMN_NAME));
                String nott = rs.getString(rs.getColumnIndex(DBHelper.NOTES_COLUMN_NOTE));

                /*to avoid memory leak i close the cursor interface*/
                if (!rs.isClosed())
                {
                    rs.close();
                }

                /*this is note view that's why i hide a save button*/
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText(nam);
                name.setFocusable(false);
                name.setClickable(false);

                note.setText(nott);
                note.setFocusable(false);
                note.setClickable(false);
            }
            /*when i create a new note... */
            else
            {
                Toast.makeText(getApplicationContext(), "Tworzenie", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*create a option menu with conteins two button, edit note and remove note*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.display_contact, menu);
        return true;
    }

    /*actions for using a menu button */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            /*when i press edit button*/    
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);


                note.setEnabled(true);
                note.setFocusableInTouchMode(true);
                note.setClickable(true);

                return true;
            /*when i press delete button*/    
            case R.id.Delete_Contact:
                
                /*create a popap with two options; 
                Yes = delete the note and return to the main activity; 
                No = return to remember earlier view (it depends, when I was in the editing view it will be Edit View, for example)*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteNotes(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Usunięto", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                /*when i set up that popap i must create it and show it to the screen*/
                AlertDialog d = builder.create();
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    
    /*and one method which i write directly to the save button in onClick*/
    public void run(View view)
    {
        /*no additional constructor needed; I start from defining objects*/
        Intent in = getIntent();
        Bundle extras = in.getExtras();

        /*checking intent value*/
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            
            /*when I choose already existing note...*/
            if(Value > 0)
            {
                if(mydb.updateNotes(id_To_Update,name.getText().toString(), note.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Zaktualizowano", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Błąd zapisu", Toast.LENGTH_SHORT).show();
                }

            }
            /*when I create new note...*/
            else
            {
                if(mydb.insertNotes(name.getText().toString(), note.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Stworzono notatkę", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Błąd zapisu", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
