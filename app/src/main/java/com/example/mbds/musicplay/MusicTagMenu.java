package com.example.mbds.musicplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MBDS on 06/04/2016.
 */
public class MusicTagMenu extends Activity{

    Button btnAddNfc,btnLunchMusic;
    NfcDataBaseAdapter nfcDataBaseAdapter;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);

         userName = getIntent().getExtras().getString("userName");
        System.out.println(userName);

        // Get The Refference Of Buttons
        btnAddNfc=(Button)findViewById(R.id.buttonAddTag);
        btnLunchMusic=(Button)findViewById(R.id.buttonLunchMusic);

        // Set OnClick Listener on SignUp button
        btnAddNfc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentNfc=new Intent(getApplicationContext(),AddNfcData.class);
                intentNfc.putExtra("userName",userName);
                startActivity(intentNfc);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database

    }


}
