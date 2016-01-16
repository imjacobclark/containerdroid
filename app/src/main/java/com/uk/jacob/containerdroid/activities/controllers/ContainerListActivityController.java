package com.uk.jacob.containerdroid.activities.controllers;

import com.uk.jacob.containerdroid.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.activities.controllers.interfaces.IActivityController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContainerListActivityController implements IActivityController {
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private List<String> currentActiveContainerIds;
    private List<String> currentCAdvisorIds;

    public ContainerListActivityController(final ContainerListRecyclerViewAdapter containerListRecyclerAdapter){
        this.containerListRecyclerAdapter = containerListRecyclerAdapter;
        this.currentActiveContainerIds = new ArrayList<String>();
        this.currentCAdvisorIds = new ArrayList<String>();
    }

    @Override
    public void buildInterface(Map containers) {
        containerListRecyclerAdapter.setRefreshing(true);
        currentCAdvisorIds.clear();

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
