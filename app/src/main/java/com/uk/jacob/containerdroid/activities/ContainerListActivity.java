package com.uk.jacob.containerdroid.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.uk.jacob.containerdroid.adapters.ContainerListRecyclerViewAdapter;
import com.uk.jacob.containerdroid.models.Container;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import com.uk.jacob.containerdroid.R;

import java.util.ArrayList;
import java.util.List;

public class ContainerListActivity extends ActionBarActivity {
    private RecyclerView containerListRecyclerView;
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private RecyclerView.LayoutManager containerListLayoutManager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_list);

        final SwipeRefreshLayout swiperefreshContainerListRecyclerView = (SwipeRefreshLayout) this.findViewById(R.id.swiperefresh_container_list_recyclerview);

        swiperefreshContainerListRecyclerView.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshContainerList();
                    }
                }
        );

        // Get a handle on our RecyclerView for interactin and setup
        containerListRecyclerView = (RecyclerView) findViewById(R.id.container_list_recyclerview);

        // Grab a new LayoutManager
        containerListLayoutManager = new LinearLayoutManager(this);

        // Grab a new adapter
        List<Container> containers = new ArrayList<>();
        containerListRecyclerAdapter = new ContainerListRecyclerViewAdapter(containers, this);

        // Better performance as the size of our RecyclerView does not change
        containerListRecyclerView.setHasFixedSize(true);

        // Attach our LayoutManager to our RecyclerView
        containerListRecyclerView.setLayoutManager(containerListLayoutManager);

        // Wire up adapter for RecyclerView
        containerListRecyclerView.setAdapter(containerListRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshContainerList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();

        containerListRecyclerAdapter.clear();

        CAdvisorService cAdvisorService = new CAdvisorService(containerListRecyclerAdapter);
        cAdvisorService.fetchDataFromService(context);
    }

    public void refreshContainerList(){
        containerListRecyclerAdapter.clear();

        SwipeRefreshLayout swiperefreshContainerListRecyclerView = (SwipeRefreshLayout) this.findViewById(R.id.swiperefresh_container_list_recyclerview);
        CAdvisorService cAdvisorService = new CAdvisorService(containerListRecyclerAdapter);
        cAdvisorService.fetchDataFromService(context);

        while(!containerListRecyclerAdapter.isRefreshing()){
            swiperefreshContainerListRecyclerView.setRefreshing(false);
            return;
        }
    }
}
