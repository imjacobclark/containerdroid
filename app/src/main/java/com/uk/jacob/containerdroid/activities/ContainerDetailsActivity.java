package com.uk.jacob.containerdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.models.container.stats.ContainerStatsModel;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.io.IOException;

public class ContainerDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CAdvisorService cAdvisorService = new CAdvisorService();

        try {
            ContainerStatsModel containerStats = (ContainerStatsModel) cAdvisorService.mapJsonToPojo(getIntent().getStringExtra("container_stats"), ContainerStatsModel.class);
            System.out.println(containerStats.getTimestamp());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
