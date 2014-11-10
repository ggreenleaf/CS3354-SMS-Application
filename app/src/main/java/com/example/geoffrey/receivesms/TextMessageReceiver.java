package com.example.geoffrey.receivesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class TextMessageReceiver extends BroadcastReceiver {
    public TextMessageReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle = intent.getExtras();

        Object[] messages=(Object[])bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[messages.length];

        for (int n=0; n<messages.length;n++) {
            sms[n] = SmsMessage.createFromPdu((byte[])messages[n]);
        }

        for (SmsMessage msg:sms) {
         ReceiveSMSActivity.updateMessageBox("\nFrom:"+msg.getOriginatingAddress()+"\n"+
                "Message :" +msg.getMessageBody()+"\n");
        }

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
