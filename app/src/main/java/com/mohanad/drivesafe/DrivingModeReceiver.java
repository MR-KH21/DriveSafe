package com.mohanad.drivesafe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.awareness.fence.FenceState;

/**
 * Created by mohanad.khouli on 05/11/2016.
 */

public class DrivingModeReceiver extends BroadcastReceiver {

    public static final String FENCE_RECEIVER_ACTION = BuildConfig.APPLICATION_ID + "FENCE_RECEIVER_ACTION";
    public static final String FENCE_KEY = "fence_key";


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MEE","recieved somthing man");
        if (!TextUtils.equals(FENCE_RECEIVER_ACTION, intent.getAction())) {
            Toast.makeText(context, "Received an unsupported action in FenceReceiver: action="
                    + intent.getAction(), Toast.LENGTH_SHORT).show();
            return;
        }

        // The state information for the given fence is em
        FenceState fenceState = FenceState.extract(intent);

        if (TextUtils.equals(fenceState.getFenceKey(), FENCE_KEY)) {
            String fenceStateStr;
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    fenceStateStr = "true";
                    break;
                case FenceState.FALSE:
                    fenceStateStr = "false";
                    break;
                case FenceState.UNKNOWN:
                    fenceStateStr = "unknown";
                    break;
                default:
                    fenceStateStr = "unknown value";
            }
            Toast.makeText(context, "Fence ="+fenceStateStr, Toast.LENGTH_SHORT).show();
        }
    }
}
