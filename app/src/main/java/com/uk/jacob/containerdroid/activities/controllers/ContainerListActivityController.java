package com.uk.jacob.containerdroid.activities.controllers;

import com.uk.jacob.containerdroid.activities.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.activities.controllers.interfaces.IActivityController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContainerListActivityController implements IActivityController {
    private List<String> currentActiveContainerIds;
    private List<String> currentCAdvisorIds;
    private static ContainerListActivityController instance = null;

    private ContainerListActivityController(){
        this.currentActiveContainerIds = new ArrayList<String>();
        this.currentCAdvisorIds = new ArrayList<String>();
    }

    public static ContainerListActivityController getInstance() {
        if(instance == null) {
            instance = new ContainerListActivityController();
        }

        return instance;
    }

    @Override
    public void buildInterface(Map containers, ContainerListRecyclerViewAdapter containerListRecyclerAdapter) {
        containerListRecyclerAdapter.setRefreshing(true);
        currentCAdvisorIds.clear();

        if(containerListRecyclerAdapter.getItemCount() == 0) {
            currentActiveContainerIds.clear();
        }

        Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();
        int position = 0;

        while(iterator.hasNext()){
            ContainerModel container = iterator.next().getValue();
            currentCAdvisorIds.add(container.getAliasId());

            if(!currentActiveContainerIds.contains(container.getAliasId())){
                containerListRecyclerAdapter.addItem(position, container);
                currentActiveContainerIds.add(container.getAliasId());
            }
        }

        System.out.println(currentCAdvisorIds.equals(currentActiveContainerIds));

        if(!currentCAdvisorIds.equals(currentActiveContainerIds)){

            Iterator currentActiveContainerIdsIterator = currentActiveContainerIds.iterator();
            while(currentActiveContainerIdsIterator.hasNext()){
                if(!currentCAdvisorIds.contains(currentActiveContainerIdsIterator.next())){
                    currentActiveContainerIdsIterator.remove();
                    containerListRecyclerAdapter.removeItem(position);
                }
            }
        }

        position++;
        containerListRecyclerAdapter.setRefreshing(false);
    }
}
