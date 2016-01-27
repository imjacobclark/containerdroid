package com.uk.jacob.containerdroid.repositories.interfaces;

import android.content.Context;

import java.util.Map;

public interface ICAdvisorRepository {
    interface LoadContainersCallback {
        void onContainersLoaded(Map containers);
    }

    void getContainers(LoadContainersCallback callback);

    void refreshData();
}
