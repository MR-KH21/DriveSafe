package com.drive.safe;

import android.app.Application;

/**
 * Created by mohanad.khouli on 06/11/2016.
 */

public class DriveSafe {

    public static void init(final Application application){
        final DriveSafeLifeCycleListener driveSafeLifeCycleListener = new DriveSafeLifeCycleListener(application);
        application.registerActivityLifecycleCallbacks(driveSafeLifeCycleListener);
    }


}
