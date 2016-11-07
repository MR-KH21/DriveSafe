package com.drive.safe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.awareness.fence.FenceState;

import static com.drive.safe.FencesManager.DRIVING_KEY;
import static com.drive.safe.FencesManager.FENCE_RECEIVER_ACTION;

/**
 * Created by mohanad.khouli on 05/11/2016.
 */

public class DrivingModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!TextUtils.equals(FENCE_RECEIVER_ACTION, intent.getAction())) {
            Toast.makeText(context, "Received an unsupported action in FenceReceiver: action="
                    + intent.getAction(), Toast.LENGTH_SHORT).show();
            return;
        }

        FenceState fenceState = FenceState.extract(intent);

        if (TextUtils.equals(fenceState.getFenceKey(), DRIVING_KEY)) {
            String fenceStateStr;
            switch (fenceState.getCurrentState()) {
                case FenceState.TRUE:
                    fenceStateStr = "true";
                    break;
                case FenceState.FALSE:
                    fenceStateStr = "false";
                    break;
                default:
                    fenceStateStr = "unknown value";
            }
            UpdateUIObservable.getInstance().updateValue(fenceStateStr);
        }
    }
}
