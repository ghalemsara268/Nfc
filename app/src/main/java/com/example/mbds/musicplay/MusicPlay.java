/*
package com.example.mbds.musicplay;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;

*/
/**
 * Created by MBDS on 07/04/2016.
 *//*

public class MusicPlay extends Activity {

    NfcDataBaseAdapter nfcAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check tag existence.
        TagsStorage tagsStorage = new TagsStorage(this);
        String id = TagsStorage.bytesToHex(getIntent().getByteArrayExtra(NfcAdapter.EXTRA_ID));
        String rightUrl= nfcAdapter.getNfcId(id);

        if (rightUrl.equalsIgnoreCase("NOT EXIST")) {
            finish();
            return;
        }

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //// Lancer Url http://tunein.com/radio/music/

        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://tunein.com/radio/music/"));
        startActivity(i);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
*/
