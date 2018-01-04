package com.htetznaing.callernumbertalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by HtetzNaing on 12/12/2017.
 */
public class ServiceReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(final Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("myFile",Context.MODE_PRIVATE);
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                boolean b = sharedPreferences.getBoolean("what",true);
                if (b==true) {
                    Log.d("CallerNumberTalker","Running");
                    new Play(context, eng2Mm(incomingNumber));
                }else{
                    Log.d("CallerNumberTalker","Stopped");
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);
    }

    public String eng2Mm(String s){
        s = s.replace("0","၀");
        s = s.replace("1","၁");
        s = s.replace("2","၂");
        s = s.replace("3","၃");
        s = s.replace("4","၄");
        s = s.replace("5","၅");
        s = s.replace("6","၆");
        s = s.replace("7","၇");
        s = s.replace("8","၈");
        s = s.replace("9","၉");
        return s;
    }
}