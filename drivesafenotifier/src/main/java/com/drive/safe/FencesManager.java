package com.drive.safe;

import android.app.PendingIntent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by mohanad.khouli on 07/11/2016.
 */

public class FencesManager {
    public static final String FENCE_RECEIVER_ACTION = BuildConfig.APPLICATION_ID + "DRIVING_RECEIVER_ACTION";
    public static final String DRIVING_KEY = "driving_key";

    public static void setupFences(GoogleApiClient client, PendingIntent drivingPendingIntent) {

        AwarenessFence drivingFence = DetectedActivityFence.during(DetectedActivityFence.IN_VEHICLE);
        AwarenessFence bikingFence = DetectedActivityFence.during(DetectedActivityFence.ON_BICYCLE);
        AwarenessFence headphoneFence = HeadphoneFence.during(HeadphoneState.PLUGGED_IN);//for testing reasons

        AwarenessFence drivingOrBiking = AwarenessFence.or(drivingFence, bikingFence,headphoneFence);//remove the headphone later



        // Register the fence to receive callbacks.
        Awareness.FenceApi.updateFences(
                client,
                new FenceUpdateRequest.Builder()
                        .addFence(DRIVING_KEY, drivingOrBiking, drivingPendingIntent)
                        .build())
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if(status.isSuccess()) {
                            Log.i(getClass().getName(),"registered successfully");
                        } else {
                            Log.i(getClass().getName(),"Fence could not be registered:" + status);
                        }
                    }
                });
    }
}
