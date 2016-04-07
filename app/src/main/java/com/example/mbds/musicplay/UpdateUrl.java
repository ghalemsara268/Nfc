package com.example.mbds.musicplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by MBDS on 08/04/2016.
 */
public class UpdateUrl extends Activity {


    NfcDataBaseAdapter nfcDataBaseAdapter;
    String userName ;
    String tagId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateurl);

        // get the Refferences of views
        final EditText editTextUpdate = (EditText) findViewById(R.id.editTextUpdate);

        nfcDataBaseAdapter = new NfcDataBaseAdapter(getApplicationContext());
        Button btnUpdate = (Button) findViewById(R.id.buttonUpdate);
         userName = getIntent().getExtras().getString("userName");
         tagId = getIntent().getExtras().getString("tagId");
        System.out.print(userName);
        System.out.print(tagId);
        // Set On ClickListener
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String url = editTextUpdate.getText().toString();

                nfcDataBaseAdapter.updateEntry(userName, tagId, url);


            }
        });


    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        nfcDataBaseAdapter.close();
    }

}