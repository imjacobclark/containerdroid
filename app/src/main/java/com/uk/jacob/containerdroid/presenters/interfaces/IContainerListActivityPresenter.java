package com.uk.jacob.containerdroid.presenters.interfaces;

import java.util.Map;

/**
 * Created by jacobclark on 03/02/2016.
 */
public interface IContainerListActivityPresenter {
    interface Callback {
        void callback(Map containers);
    }

    void fetchData(Callback callback);

    void refreshData(Callback callback);
}
