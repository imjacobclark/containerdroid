package com.uk.jacob.containerdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.models.container.stats.ContainerStatsModel;
import com.uk.jacob.containerdroid.repositories.CAdvisorRepository;
import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ContainerDetailsActivity extends ActionBarActivity {
    private CAdvisorRepository cAdvisorRepository = CAdvisorRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String containerAlias = getIntent().getStringExtra("container_alias");

        cAdvisorRepository.getContainers(new ICAdvisorRepository.GetContainersCallback() {
            @Override
            public void onContainersLoaded(Map containers) {
                Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();

                while(iterator.hasNext()){
                    ContainerModel container = iterator.next().getValue();

                    if(container.getAliasId().equals(containerAlias)){
                        System.out.println(container.getSpec().getImage());
                    }
                }
            }
        });
    }

}
