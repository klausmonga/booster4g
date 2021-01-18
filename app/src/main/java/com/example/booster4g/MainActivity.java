package com.example.booster4g;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Switch turn= (Switch) findViewById(R.id.turn);
        Switch up= (Switch) findViewById(R.id.up);
        Switch down= (Switch) findViewById(R.id.down);
        up.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    postAction("/t/up");
                }else{
                    postAction("/t/2");
                }

            }
        });
        down.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    postAction("/t/down");
                }else{
                    postAction("/t/2");
                }
            }
        });
        turn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    postAction("/t/1");
                }
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),contact.class));
            }
        });

    }
    public void postAction(String action){
        Log.e("status",String.valueOf(action));
        String url = "";
        final String domain = new Connect().getDomain(getApplicationContext());
        final TextView report=findViewById(R.id.report);
        if (domain==null){
            report.setVisibility(View.VISIBLE);
            report.setText("YOU MUST SET THE ANTENNA IP ADDRESS PLEASE");
            report.setTextColor(Color.RED);
        }else{
            url=new Connect().getDomain(getApplicationContext())+action;
            final StringRequest stringRequest;
            stringRequest = new StringRequest(Request.Method.GET,
                    url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("espresponse",response);
                    report.setVisibility(View.VISIBLE);
                    report.setText("ANTENNA IP ADDRESS:"+domain.replace("http://","")+"\n ANTENNA RESPONSE:"+response);
                    report.setTextColor(Color.YELLOW);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    report.setVisibility(View.VISIBLE);
                    report.setText("NETWORK ERROR :"+error.getMessage());
                    report.setTextColor(Color.RED);
                    Log.e("Status remote"," erreur de volley: ("+error.getMessage()+")");
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        }

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,pref.class));
            return true;
        }else{
            SharedPreferences sharedpreferences = getSharedPreferences("ip", Context.MODE_PRIVATE);
            String url = "http://"+sharedpreferences.getString("ipmodem",null);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
