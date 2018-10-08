package rich.on.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

/**
 * Created by Winardi on 11/16/2017.
 */

public class IncomingSms extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;

        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                smsm = new SmsMessage[pdus.length];
                for (int i = 0; i < smsm.length; i++) {
                    smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    String sender = smsm[i].getOriginatingAddress();
                    //Check here sender is yours
                    if (sender.matches("\\+44 7533 007337") || sender.matches("\\+447533007337") || sender.equals("BAND") || sender.equals("KONSEL") || sender.equals("SECURE") || sender.equals("Alert") || sender.equals("Alert") || sender.equals("VGSMS")) {
                        Intent smsIntent = new Intent("otp");
                        smsIntent.putExtra("message", String.valueOf(smsm[i].getMessageBody()));
                        LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                    }
                }
            }
        }
    }
}