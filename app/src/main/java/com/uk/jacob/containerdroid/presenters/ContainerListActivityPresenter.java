package com.uk.jacob.containerdroid.presenters;

import com.uk.jacob.containerdroid.presenters.interfaces.IContainerListActivityPresenter;
import com.uk.jacob.containerdroid.repositories.CAdvisorRepository;
import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;

import java.util.Map;

public class ContainerListActivityPresenter implements IContainerListActivityPresenter{
    private CAdvisorRepository cAdvisorRepository = CAdvisorRepository.getInstance();

    public void fetchData(final Callback callback) {
        cAdvisorRepository.getContainers(new ICAdvisorRepository.GetContainersCallback() {
            @Override
            public void onContainersLoaded(Map containers) {
                callback.callback(containers);
            }
        });
    }

    public void refreshData(final Callback callback) {
        cAdvisorRepository.refreshData(new ICAdvisorRepository.GetContainersCallback() {
            @Override
            public void onContainersLoaded(Map containers) {
                callback.callback(containers);
            }
        });
    }
}
