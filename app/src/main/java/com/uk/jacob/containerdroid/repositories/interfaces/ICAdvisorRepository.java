package com.uk.jacob.containerdroid.repositories.interfaces;

import java.util.Map;

public interface ICAdvisorRepository {
    interface GetContainersCallback {
        void onContainersLoaded(Map containers);
    }

    void getContainers(GetContainersCallback callback);

    void refreshData(GetContainersCallback callback);
}
