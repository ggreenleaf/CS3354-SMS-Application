package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ReceiveSMSActivity extends Activity {

    static TextView messageBox;
    public static final int THREAD_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_sms);
        messageBox = (TextView)findViewById(R.id.messageBox);

        Button viewMsgBtn = (Button) findViewById(R.id.viewMessages);
        final ListView listview = (ListView) findViewById(R.id.convoListView);
        ArrayList<String> list = new ArrayList<String>();
        //Listening to button event
//        viewMsgBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                //Starting a new Intent
//                Intent nextScreen = new Intent(getApplicationContext(),InboxActivity.class );
//                nextScreen.putExtra("thread_id", "7");
//                startActivity(nextScreen);
//            }
//        });

        Cursor cur = getContentResolver().query(Uri.parse("content://sms/conversations"),null,null,null,null);
        cur.moveToFirst();
        do{
           list.add(cur.getString(0));
        }while(cur.moveToNext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tid = (String) listview.getItemAtPosition(position);
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(),InboxActivity.class );
                nextScreen.putExtra("thread_id", tid);
                startActivity(nextScreen);
            }
        });
    }










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
