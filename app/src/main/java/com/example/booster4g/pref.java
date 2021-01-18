package com.example.booster4g;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.QuickContactBadge;

public class pref extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText ip=findViewById(R.id.ipesp);
        final EditText ipmodem=findViewById(R.id.ipmodem);
        SharedPreferences sharedpreferences = getSharedPreferences("ip", Context.MODE_PRIVATE);
        if (sharedpreferences.getString("ip",null)!=null){
            ip.setText(sharedpreferences.getString("ip",null));
        }
        if (sharedpreferences.getString("ipmodem",null)!=null){
            ipmodem.setText(sharedpreferences.getString("ipmodem",null));
        }
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("ip", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ip", ip.getText().toString());
                editor.putString("ipmodem", ipmodem.getText().toString());
                if (editor.commit())
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });
    }

}
