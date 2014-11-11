package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class InboxActivity extends Activity {
    private static  TextView inbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        inbox = (TextView)findViewById(R.id.inboxView);
        Cursor cur = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);
        cur.moveToFirst();
        do {
            String msgData = "";
            //idx 2= address idx 12 = "body"
            msgData += "From: " +cur.getString(2)+"\n";
            msgData += "Body: " + cur.getString(12) + "\n";
            inbox.append(msgData);
        }while(cur.moveToNext());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
