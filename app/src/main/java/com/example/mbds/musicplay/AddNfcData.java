package com.example.mbds.musicplay;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by MBDS on 06/04/2016.
 */
public class AddNfcData extends Activity {

    EditText editTextNameTag,editTextUrl;
    Button btnAddTag;
    TextView textViewIdTag;
    NfcDataBaseAdapter nfcDataBaseAdapter;


    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    byte[] mId;
    TagsStorage mTags;
    String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnfc);


        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mTags = new TagsStorage(this);
//        System.out.println(mNFCTechLists[1][0]);
        // get Instance  of Database Adapter
        nfcDataBaseAdapter=new NfcDataBaseAdapter(getApplicationContext());


        // Get Refferences of Views
        textViewIdTag=(TextView)findViewById(R.id.idTag);
      System.out.println(textViewIdTag.getText().toString());
        editTextNameTag=(EditText)findViewById(R.id.tagName);

        editTextUrl=(EditText)findViewById(R.id.urlMusic);
        userName = getIntent().getExtras().getString("userName");
        System.out.println("user2"+userName);

        btnAddTag=(Button)findViewById(R.id.AddTag);
        btnAddTag.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String myName = editTextNameTag.getText().toString();
                String myUrl = editTextUrl.getText().toString();
                String monId = textViewIdTag.getText().toString();

                System.out.println("user3"+userName);

                // check if any of the fields are vaccant
                if (myUrl.equals("") || myName.equals("") || monId.equals("")|| userName.equalsIgnoreCase("") ){
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // Save the Data in Database
                    System.out.println("monId" + monId);
                    nfcDataBaseAdapter.insertEntry(monId,myName,myUrl,userName);
                    Toast.makeText(getApplicationContext(), "Tag Successfully Created ", Toast.LENGTH_LONG).show();
                }
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
        textViewIdTag.setText(TagsStorage.bytesToHex(mId));
        textViewIdTag.setError(null);
    }

    @Override
    public void onPause() {
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

