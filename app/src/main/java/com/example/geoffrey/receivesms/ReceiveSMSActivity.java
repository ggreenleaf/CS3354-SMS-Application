package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ReceiveSMSActivity extends Activity {

    static TextView messageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_sms);
        messageBox = (TextView)findViewById(R.id.messageBox);

        Button viewMsgBtn = (Button) findViewById(R.id.viewMessages);

        //Listening to button event

        viewMsgBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent

                Intent nextScreen = new Intent(getApplicationContext(),InboxActivity.class );

             //   nextScreen.putExtra("address","address");

//                Log.e("n", "name" + "." +"email");

                startActivity(nextScreen);
            }
        });
    }

    public static void updateMessageBox(String msg)
    {
        messageBox.append(msg);
    }

//    public void saveSMSToInbox(SmsMessage sms) {
//        ContentValues values = new ContentValues();
//        values.put("address", sms.getOriginatingAddress());
//        values.put("body", sms.getDisplayMessageBody());
//
//        this.getContentResolver().insert(
//                    Uri.parse("content:/sms/inbox"), values);
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_sm, menu);
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
