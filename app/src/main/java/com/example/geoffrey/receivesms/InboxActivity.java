package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.MergeCursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private static ListView msgListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Bundle b = getIntent().getExtras();
        tid = b.getString("thread_id");
        msgListview = (ListView)findViewById(R.id.msg_list);
        Button replyBtn = (Button)findViewById(R.id.replyButton);
        //displaying the conversations

        convo = getConversationMessages();
      // for(String msg: convo)
      //     inbox.append(msg+"\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_list_item_1, convo);

        msgListview.setBackgroundColor(Color.BLACK);
        msgListview.setAdapter(adapter);
        msgListview.setSelection(convo.size());
        //add listener for reply button
        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   String number = tid;
                   EditText textInput = (EditText)findViewById(R.id.editText);
                   String msg = textInput.getText().toString();
                   textInput.setText("");
                   sendSMSMessage(number, msg);
                   v.invalidate(); //should refresh
                   finish();
                   startActivity(getIntent());
            }
        });





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
    //Returns the messages sent to the conversation with id:thread_id (phone number)

    protected ArrayList<String> getConversationMessages() {
        Uri inboxUri = Uri.parse(INBOX_URI);
        Uri outboxUri = Uri.parse(OUTBOX_URI);
//        MergeCursor c = new MergeCursor();
        ArrayList<String> messages = new ArrayList<String>();
        selectionArgs[0] = tid;
        //probably a better way to query both inbox and outbox
        Cursor curIn = getContentResolver().query(inboxUri, null, selectionClause, selectionArgs, "DATE desc");
        Cursor curOut = getContentResolver().query(outboxUri, null, selectionClause, selectionArgs, "Date desc");
        for (int i=0; i<curIn.getColumnCount();i++)
        {
            Log.e(curIn.getColumnName(i),"colName");
        }
        //iterate through both queries and add text body to
        //the end of an ArrayList sorted by date
        curIn.moveToFirst(); curOut.moveToFirst();
        while(!curIn.isClosed() || !curOut.isClosed())
        {
            String msg = "";
            String iTime = "-1"; //use negative one to compare if one cursor is closed
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
                    messages.add(0, msg);
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
                    msg = "\t\t\t" + curOut.getString(SMS_BODY);
                    messages.add(0, msg);
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

    protected void sendSMSMessage(String number, String textBody)
    {
        SmsManager sms = SmsManager.getDefault();
        try {
            sms.sendTextMessage(number, null, textBody, null, null);
        }
        catch(Exception e)
        {
            Log.e(e.toString(),"exception ");
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
