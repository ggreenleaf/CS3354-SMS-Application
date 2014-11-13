package com.example.geoffrey.receivesms;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class TextMessageReceiver extends BroadcastReceiver {
    public TextMessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();
        //check for certain SMS_RECEIVED_ACTION
        Object[] messages=(Object[])bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];

        for (int n=0; n<messages.length;n++) {
            sms[n] = SmsMessage.createFromPdu((byte[])messages[n]);
        }

         Toast.makeText(context,sms[0].getDisplayMessageBody(),Toast.LENGTH_SHORT ).show();
         
//        throw new UnsupportedOperationException("Not yet implemented");
    }

}
