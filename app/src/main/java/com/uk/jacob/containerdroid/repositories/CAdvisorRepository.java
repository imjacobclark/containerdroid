package com.uk.jacob.containerdroid.repositories;

import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.util.Map;

public class CAdvisorRepository implements ICAdvisorRepository {
    private CAdvisorService cAdvisorService = null;
    private static CAdvisorRepository instance = null;

    private Map mContainers;

    public CAdvisorRepository(){
    }

    public static CAdvisorRepository getInstance(){
        if(instance == null){
            instance = new CAdvisorRepository();
            return instance;
        }
        return instance;
    }

    @Override
    public void getContainers(final GetContainersCallback callback) {
        if(cAdvisorService == null){
            cAdvisorService = new CAdvisorService();

            cAdvisorService.fetchDataFromService(new CAdvisorRepository.GetContainersCallback(){
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
    public void refreshData(final GetContainersCallback callback) {
        cAdvisorService = new CAdvisorService();

        cAdvisorService.fetchDataFromService(new CAdvisorRepository.GetContainersCallback(){
            @Override
            public void onContainersLoaded(Map containers) {
                mContainers = containers;
                callback.onContainersLoaded(containers);
            }
        });
    }
}
