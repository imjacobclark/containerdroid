package com.uk.jacob.containerdroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.uk.jacob.containerdroid.R;

public class ContainerDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle((String) getIntent().getSerializableExtra("containerAlias"));
    }

}
