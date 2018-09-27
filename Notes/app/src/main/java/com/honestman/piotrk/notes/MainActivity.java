package com.honestman.piotrk.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    
    /*Creating ListViev and DBHelper Object*/
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*context of DBHelper set to MainActivity*/
        mydb = new DBHelper(this);
        
        /*create array list with all notes from DBHelper*/
        ArrayList array_list = mydb.getAllNotes();
        
        /*create adapter which set one note to speccyfic layout*/
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.notatka, array_list);

        /*insert all together on our ListView*/
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);



        /*What would happen when i click one note from a list*/
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                /*to avoid nullPointerExeption i increment arg2 by one*/
                int id_To_Search = arg2 + 1;
                
                /*use bundle and intent to pass note id to show a chosen note */
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    
    /*create option manu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /*create respond on option manu buttons 
    (in this case i got only one button in menu but switch statment is good for adding some more buttons)*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId())
        {
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*useful code when i return from note to MainActivity; when i back i got only a MainActivity*/
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

}





