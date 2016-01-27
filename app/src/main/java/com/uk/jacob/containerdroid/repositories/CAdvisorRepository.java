package com.uk.jacob.containerdroid.repositories;

import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.util.Map;

public class CAdvisorRepository implements ICAdvisorRepository {
    private CAdvisorService cAdvisorService = null;
    private Map mContainers;

    @Override
    public void getContainers(final LoadContainersCallback callback) {
        if(cAdvisorService == null){
            cAdvisorService = new CAdvisorService();

            cAdvisorService.fetchDataFromService(new CAdvisorRepository.LoadContainersCallback(){
                @Override
                public void onContainersLoaded(Map containers) {
                    mContainers = containers;
                    callback.onContainersLoaded(containers);
                }
            });
        }else{
            callback.onContainersLoaded(mContainers);
        }
    }

    @Override
    public void refreshData() {
        cAdvisorService = new CAdvisorService();

        cAdvisorService.fetchDataFromService(new CAdvisorRepository.LoadContainersCallback(){
            @Override
            public void onContainersLoaded(Map containers) {
                mContainers = containers;
            }
        });
    }
}
