package com.example.geoffrey.receivesms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SendSMSActivity extends Activity {

    //String name = "Test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        EditText numberBox = (EditText)findViewById(R.id.editText1);
        numberBox.getText().equals("");

        //Manifest.permission.SEND_SMS = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_sm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendMessage(View v)
    {
        //Context context = getApplicationContext();

        EditText inputBox = (EditText)findViewById(R.id.editText2);
        EditText numberBox = (EditText)findViewById(R.id.editText1);

        String numbers = numberBox.getText().toString();
        Log.v("Numbers are", numbers);

        //Editable phoneNo = numberBox.getText();
        Editable message = inputBox.getText();

        String[] arr = numbers.split("; ");

        for(int i=0; i<arr.length; i++)
        {
            Log.v("arr"+i, arr[i]);
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();

            for(int i=0; i<arr.length; i++)
            {
                smsManager.sendTextMessage(arr[i]+"", null, message+"", null, null);
            }

            Toast.makeText(getApplicationContext(),
                    "SMS sent.",
                    Toast.LENGTH_LONG).show();
            numberBox.setText("");
            inputBox.setText("");
        }
        catch (Exception e)
        {
            Log.v("caught error", e+"");
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //*/
    }

    private static final int CONTACT_PICKER_RESULT = 1001;

    public void selectContact(View view)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
        contactPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT); //numberBox.setText("9723699408");
    }

    static String DEBUG_TAG = "Drew's Errors";
    int damnIndex;
    String damnNumber;
//*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int num =0;
        try
        {
            String phoneNo = null ;
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            num =1;
            int  phoneIndex = damnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            num =2;
            phoneNo = damnNumber = cursor.getString(phoneIndex);
            num =999;

            EditText numberBox = (EditText)findViewById(R.id.editText1);

            if(numberBox.getText().length() == 0)
            {
                numberBox.setText(phoneNo);
            }
            else
            {
                numberBox.setText(numberBox.getText()+"; "+phoneNo);
            }
        }
        catch(Exception e)
        {
            Log.v(DEBUG_TAG, ContactsContract.CommonDataKinds.Phone.NUMBER+ " "+damnIndex+"  in exception :"+num);
        }
        Log.v("end of find contact", "Phone index is "+damnIndex+ " number is "+damnNumber);
    }

    public void getSMS()
    {
        Log.v("testLog", "get Message");
    }








}
