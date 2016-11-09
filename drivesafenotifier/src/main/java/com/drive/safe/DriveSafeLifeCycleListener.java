package com.drive.safe;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Observable;
import java.util.Observer;

import static com.drive.safe.FencesManager.FENCE_RECEIVER_ACTION;

/**
 * Created by mohanad.khouli on 06/11/2016.
 */

public class DriveSafeLifeCycleListener implements Application.ActivityLifecycleCallbacks, Observer {

    private GoogleApiClient googleApiClient;
    private PendingIntent drivingPendingIntent;
    private DrivingModeReceiver drivingFenceReceiver;
    private Activity currentActivity;
    private boolean isReceiverRegistered = false;

    public DriveSafeLifeCycleListener(final Context context) {

        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Awareness.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                        // Set up the PendingIntent that will be fired when the fence is triggered.
                        Intent intent = new Intent(FENCE_RECEIVER_ACTION);
                        drivingPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

                        // The broadcast receiver that will receive intents when a fence is triggered.
                        drivingFenceReceiver = new DrivingModeReceiver();
                        context.registerReceiver(drivingFenceReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));
                        isReceiverRegistered = true;
                        FencesManager.setupFences(googleApiClient, drivingPendingIntent);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.i(getClass().getName(),"Connection Suspended");
                    }
                })
                .build();
        googleApiClient.connect();
        UpdateUIObservable.getInstance().addObserver(this);
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
        if (drivingFenceReceiver != null && !isReceiverRegistered) {
            activity.registerReceiver(drivingFenceReceiver, new IntentFilter(FENCE_RECEIVER_ACTION));
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        currentActivity = null;
        if (drivingFenceReceiver != null) {
            try {
                activity.unregisterReceiver(drivingFenceReceiver);
                isReceiverRegistered = false;
            }catch (Exception ex){
                ex.printStackTrace();//in case its unregistered yet
            }
        }

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void update(Observable observable, Object o) {

        if (currentActivity != null && "true".equals(o.toString())) {
            Popup showMessagePopup = new Popup(currentActivity, Html.fromHtml( currentActivity.getString(R.string.msg)), new Popup.Listener() {
                @Override
                public void onCancel(Popup popup) {

                    popup.performAnimationToHide();
                }

                @Override
                public void onCloseAppSelected(Popup popup) {
                    ((ViewGroup)popup.getParent()).removeView(popup);
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    currentActivity.startActivity(i);
                }
            });
            currentActivity.addContentView(showMessagePopup, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            showMessagePopup.performAnimationToShow();

        }


    }
}
