package com.uk.jacob.containerdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.fasterxml.jackson.core.SerializableString;
import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.models.ContainerSpecModel;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.io.IOException;
import java.io.Serializable;

public class ContainerDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CAdvisorService cAdvisorService = new CAdvisorService();

        try {
            System.out.println(cAdvisorService.mapJsonToPojo(getIntent().getStringExtra("container_specs")).getCreated());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
