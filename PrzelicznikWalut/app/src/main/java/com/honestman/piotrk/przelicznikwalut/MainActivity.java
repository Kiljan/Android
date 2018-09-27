package com.honestman.piotrk.przelicznikwalut;


import java.net.URL;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/*I implemented a AdapterView because in earlier app version i got two Spinners. That whey allow me better variable management.*/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /*create objects*/
    Spinner listaWalutDwa;
    TextView naWalute;
    TextView informacjaOgolna;
    NodeList nodelist;
    ProgressDialog pDialog;    
    
    /*create empty string for further usage*/
    String text2 = "";

    /*Insert image URL*/
    String URL = "http://www.floatrates.com/daily/pln.xml";

    /*create arrayList of strings for further usage*/
    ArrayList<String> arrayList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*function which does not allow to display expired*/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /*defining objects*/
        listaWalutDwa = (Spinner) findViewById(R.id.walutyDoWyboruDwa);
            /*context to our implementation AdapterView.OnItemSelectedListener*/
        listaWalutDwa.setOnItemSelectedListener(this);
        naWalute = (TextView) findViewById(R.id.ustawienieWyniku);
        
        /*and run our download xml method*/
        new DownloadXML().execute(URL);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int i;
        
        /*objects that exist only in the current method*/
        Spinner spinner = (Spinner) parent;
        String wybranaWaluta;

        /*only one spinner, that's why i use only one if statement with id of that spinner*/
        if (spinner.getId() == R.id.walutyDoWyboruDwa) {
            /*get item at selected position*/
            wybranaWaluta = (String) parent.getItemAtPosition(position);
            /*i pass above value to my global variable*/
            text2 = wybranaWaluta;
        }
        
       /*defining global object*/
        informacjaOgolna = (TextView) findViewById(R.id.informacjaOgolnaView);
        informacjaOgolna.setText("Kurs PLN/" + text2 + " wynosi: ");

        /*i check ArrayList and i look for currency that i chose from spinner */
        for (i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).contains(text2)) {
                /*xml tag contains much more information, that's why I use string operation*/
                naWalute.setText(arrayList.get(i).substring(8, 14));
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    /*for my purposes, nothing happened here*/
    }

    /*DownloadXML AsyncTask*/
    private class DownloadXML extends AsyncTask<String, Void, Void> {


        @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /*This method is required but i hide their results anyway*/
                
                /* Create a progressbar */
                pDialog = new ProgressDialog(MainActivity.this);
                /* Set progressbar title */
                pDialog.setTitle("Kursy walut");
                /* Set progressbar message */
                pDialog.setMessage("Oczekiwanie na waluty...");
                pDialog.setIndeterminate(false);
                /* Hide progressbar */
                pDialog.hide();
                /* Show progressbar*/
                /*pDialog.show()*/
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                /*creating document from xml on url which contains specific tag*/
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                /* Locate the Tag Name */
                nodelist = doc.getElementsByTagName("item");

            } catch (Exception e) {
                nodelist = null;
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void args) {

            if (nodelist == null)
            {
                /*if no internet connection, program prints the message*/
                Toast.makeText(getApplicationContext(), "Wymagane jest połączenie z internetem. Waluty nie zostały pobrane.", Toast.LENGTH_LONG).show();

            } else
            {
                /*if internet connection is true I put all title tag to one array of strings defined earlier*/
                for (int i = 0; i < nodelist.getLength(); i++)
                {
                    Node nNode = nodelist.item(i);
                    Element eElement = (Element) nNode;

                    if (nNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        arrayList.add(getNode("title", eElement));
                    }
                }
            }

            /* Close progressbar */
            pDialog.dismiss();
        }
    }

    /* getNode function */
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    /*creating option manu*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    /*choose Activity to start when defined item is selected*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.id_waluty_info:
                Intent i = new Intent(MainActivity.this, SlownikMain.class);
                startActivity(i);
                return true;
            case R.id.id_aplikacja_info:
                Intent j = new Intent(MainActivity.this, InformacjeMain.class);
                startActivity(j);

            default:
                return super.onOptionsItemSelected(item);


        }
    }



}




