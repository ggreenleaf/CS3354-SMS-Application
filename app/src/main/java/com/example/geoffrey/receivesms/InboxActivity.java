package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.MergeCursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class InboxActivity extends Activity {

    private static final String INBOX_URI = "content://sms/inbox";
    private static final String OUTBOX_URI = "content://sms/sent";
    private static  TextView inbox;
    private static final int SMS_ADDRESS = 2;
    private static final int SMS_BODY = 12;
    private static String tid = "";
    private static String[] selectionArgs = {""};
    private static String searchString = "";
    private static String selectionClause = "address = ?";
    private static ArrayList<String> convo = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Bundle b = getIntent().getExtras();
        tid = b.getString("thread_id");
        inbox = (TextView)findViewById(R.id.inboxView);
//        String [] mSelectionArgs =  {""};
//        String mSearchString = tid;


//        selectionArgs[0] = tid;

//        Cursor cur = getContentResolver().query(Uri.parse(INBOX_URI),null,selectionClause,selectionArgs,null);
//        cur.moveToFirst();
//        for (int i=0; i<cur.getColumnCount(); i++)
//            Log.e(cur.getColumnName(i),"colInfo");
//        do {
//            String msgData = "";
//            //idx 2= address idx 12 = "body"
//            msgData += cur.getString(4) + " " + cur.getString(SMS_BODY) + "\n";
//
//            inbox.setTextColor(Color.BLUE);
//            inbox.append(msgData);
//        }while(cur.moveToNext());
//        cur.close();
        //getSentMessages();
        convo = getConversationMessages();
       for(String msg: convo)
           inbox.append(msg+"\n");
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
    //Returns the messages sent to the conversation with id:thread_id

    protected ArrayList<String> getConversationMessages() {
        Uri inboxUri = Uri.parse(INBOX_URI);
        Uri outboxUri = Uri.parse(OUTBOX_URI);
//        MergeCursor c = new MergeCursor();
        ArrayList<String> messages = new ArrayList<String>();
        selectionArgs[0] = tid;
        Cursor curIn = getContentResolver().query(inboxUri, null, selectionClause, selectionArgs, null);
        Cursor curOut = getContentResolver().query(outboxUri, null, selectionClause, selectionArgs, null);
//
//        if (curIn.getCount() != 0) {
//            curIn.moveToFirst();
//            do {
//                messages.add("I----"+curIn.getString(4) + " " + curIn.getString(SMS_BODY));
//            } while (curIn.moveToNext());
//        }
//        curIn.close();
//
//        if (curOut.getCount() != 0) {
//            curOut.moveToFirst();
//            do {
//                messages.add("O----"+curOut.getString(4) + " " + curOut.getString(SMS_BODY));
//            } while (curOut.moveToNext());
//        }
//        curOut.close();

        curIn.moveToFirst(); curOut.moveToFirst();
        while(!curIn.isClosed() || !curOut.isClosed())
        {
            String msg = "";
            String iTime = "-1";
            String oTime = "-1";

            if (!curIn.isClosed()) {
                if (curIn.getCount() != 0)
                    iTime = curIn.getString(4);
                else
                    curIn.close();
            }
            if(!curOut.isClosed()) {
                if (curOut.getCount() != 0)
                    oTime = curOut.getString(4);
                else
                    curOut.close();
            }

            double iT = Double.parseDouble(iTime);
            double oT = Double.parseDouble(oTime);

            if( iT > oT)
            {
                if (!curIn.isClosed()) {
                    msg = curIn.getString(SMS_BODY);
                    messages.add(msg+" "+iTime);
                    if (curIn.isLast()) {
                        curIn.close();
                        continue;
                    } else {
                        curIn.moveToNext();
                    }
                }
            }
            else {
                if (!curOut.isClosed()) {
                    msg = curOut.getString(SMS_BODY);
                    messages.add(msg+" "+oTime);
                    if (curOut.isLast()) {
                        curOut.close();
                        continue;
                    } else {
                        curOut.moveToNext();
                    }
                }
            }
        }


    return messages;
    }

}
