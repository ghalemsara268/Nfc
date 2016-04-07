package com.example.mbds.musicplay;

import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MBDS on 06/04/2016.
 */
public class MusicTagMenu extends Activity{

    Button btnAddNfc,btnLunchMusic;
    NfcDataBaseAdapter nfcDataBaseAdapter;
    String userName;
    //NfcDataBaseAdapter nfcDataBaseAdapterAdapter;
    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    byte[] mId;
    TagsStorage mTags;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music);
        nfcDataBaseAdapter=new NfcDataBaseAdapter(getApplicationContext());
         userName = getIntent().getExtras().getString("userName");
        System.out.println(userName);

        mAdapter = NfcAdapter.getDefaultAdapter(this);


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

        // Set OnClick Listener on SignUp button
        btnLunchMusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                /// CUpdate URL

                Intent intentUPDATE=new Intent(getApplicationContext(),UpdateUrl.class);
                intentUPDATE.putExtra("userName",userName);
                intentUPDATE.putExtra("tagId",TagsStorage.bytesToHex(mId));
                startActivity(intentUPDATE);


            }
        });
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null)
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    public void onNewIntent(Intent intent) {
        mId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        mTags = new TagsStorage(this);
        String id=TagsStorage.bytesToHex(mId);
        String rightUrl= nfcDataBaseAdapter.getNfcId(id,userName);
        System.out.println("user"+userName);
         System.out.println("right" + rightUrl);
        System.out.println("id" + id);
        System.out.println("url" + nfcDataBaseAdapter.getUrl(userName));
        if (rightUrl.equalsIgnoreCase("NOT EXIST")==true) {
            Toast.makeText(MusicTagMenu.this, "Dsl,le tag n'existe pas :( ajoutez le en cliquant sur ADDTAG", Toast.LENGTH_LONG).show();
            return;
        }


        //// Lancer Url http://tunein.com/radio/music/

        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(rightUrl));
        startActivity(i);

    }






    @Override
    public void onPause()
    {
        super.onPause();
        if (mAdapter != null)
            mAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        nfcDataBaseAdapter.close();
    }

}
