package com.example.booster4g;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.security.ProviderInstaller;

public class Connect {
    public  String getDomain(Context context){
        updateAndroidSecurityProvider(context);
        SharedPreferences sharedpreferences = context.getSharedPreferences("ip", Context.MODE_PRIVATE);
        if (sharedpreferences.getString("ip",null)==null)
            return null;

        return "http://"+sharedpreferences.getString("ip",null);

    }
    private void updateAndroidSecurityProvider(Context context) {
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
