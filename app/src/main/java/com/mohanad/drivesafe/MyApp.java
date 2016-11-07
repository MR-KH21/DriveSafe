package com.mohanad.drivesafe;

import android.app.Application;

import com.drive.safe.DriveSafe;


/**
 * Created by mohanad.khouli on 05/11/2016.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DriveSafe.init(this);


    }

}
