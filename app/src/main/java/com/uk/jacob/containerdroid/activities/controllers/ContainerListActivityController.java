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
        this.currentActiveContainerIds = new ArrayList<>();
        this.currentCAdvisorIds = new ArrayList<>();
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

        Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();
        int position = 0;

        if(containerListRecyclerAdapter.getItemCount() == 0) {
            currentActiveContainerIds.clear();
        }

        while(iterator.hasNext()){
            renderIntoActivity(containerListRecyclerAdapter, position, iterator.next().getValue());
        }

        if(!currentCAdvisorIds.equals(currentActiveContainerIds)){
            resyncActivityData(containerListRecyclerAdapter, position, currentActiveContainerIds.iterator());
        }

        position++;
        containerListRecyclerAdapter.setRefreshing(false);
    }

    private void resyncActivityData(ContainerListRecyclerViewAdapter containerListRecyclerAdapter, int position, Iterator currentActiveContainerIdsIterator) {
        while(currentActiveContainerIdsIterator.hasNext()){
            if(!currentCAdvisorIds.contains(currentActiveContainerIdsIterator.next())){
                currentActiveContainerIdsIterator.remove();
                containerListRecyclerAdapter.removeItem(position);
            }
        }
    }

    private void renderIntoActivity(ContainerListRecyclerViewAdapter containerListRecyclerAdapter, int position, ContainerModel container) {
        currentCAdvisorIds.add(container.getAliasId());

        if(!currentActiveContainerIds.contains(container.getAliasId())){
            containerListRecyclerAdapter.addItem(position, container);
            currentActiveContainerIds.add(container.getAliasId());
        }
    }
}
