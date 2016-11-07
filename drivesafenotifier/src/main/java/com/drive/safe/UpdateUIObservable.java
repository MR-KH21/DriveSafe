package com.drive.safe;

import java.util.Observable;

/**
 * Created by mohanad.khouli on 07/11/2016.
 */

public class UpdateUIObservable extends Observable {

    private static UpdateUIObservable instance = new UpdateUIObservable();

    public static UpdateUIObservable getInstance() {
        return instance;
    }

    private UpdateUIObservable() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
