package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class InboxActivity extends Activity {
    private static  TextView inbox;
    private static final int SMS_ADDRESS = 2;
    private static final int SMS_BODY = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Bundle b = getIntent().getExtras();
        String tid = b.getString("thread_id");
        inbox = (TextView)findViewById(R.id.inboxView);
        String [] mSelectionArgs =  {""};
        String mSearchString = tid;

        String mSelectionClause = "thread_id = ?";
        mSelectionArgs[0] = mSearchString;

    //Cursor cur = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);
      Cursor cur = getContentResolver().query(Uri.parse("content://sms/inbox"),null,mSelectionClause,mSelectionArgs,null);

        cur.moveToFirst();
        do {
            String msgData = "";
            //idx 2= address idx 12 = "body"
            msgData += "From: " +cur.getString(SMS_ADDRESS)+"\n";
            msgData += "Body: " + cur.getString(SMS_BODY) + "\n";

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
