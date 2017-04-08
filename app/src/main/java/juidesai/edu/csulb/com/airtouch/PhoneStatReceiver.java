package juidesai.edu.csulb.com.airtouch;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by jmd19 on 4/7/2017.
 */

public class PhoneStatReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStatReceiver";

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    boolean status;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    public void onReceive(Context context, Intent intent) {

        status = context.getSharedPreferences("AirTouch",Context.MODE_PRIVATE).getBoolean("callflash",false);

        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){

            incomingFlag = false;

            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            Log.i(TAG, "call OUT:"+phoneNumber);

        }else{

            TelephonyManager tm =

                    (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);



            switch (tm.getCallState()) {

                case TelephonyManager.CALL_STATE_RINGING:

                    incomingFlag = true;

                    incoming_number = intent.getStringExtra("incoming_number");

                    if(status==true)
                    {
                        new Features(context).flashon();
                    }

                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:

                    if(incomingFlag){
                        if(status==true)
                            new Features(context).flashoff();

                        Log.i(TAG, "incoming ACCEPT :"+ incoming_number);

                    }

                    break;



                case TelephonyManager.CALL_STATE_IDLE:

                    if(incomingFlag){
                        if(status==true)
                            new Features(context).flashoff();

                        Log.i(TAG, "incoming IDLE");

                    }

                    break;

            }

        }
    }
}
