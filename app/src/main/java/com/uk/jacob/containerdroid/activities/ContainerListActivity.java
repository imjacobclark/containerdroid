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
import com.uk.jacob.containerdroid.models.ContainerModel;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.repositories.CAdvisorRepository;
import com.uk.jacob.containerdroid.repositories.interfaces.ICAdvisorRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContainerListActivity extends ActionBarActivity {
    private RecyclerView containerListRecyclerView;
    private ContainerListRecyclerViewAdapter containerListRecyclerAdapter;
    private RecyclerView.LayoutManager containerListLayoutManager;

    private SwipeRefreshLayout swiperefreshContainerListRecyclerView;

    private CAdvisorRepository cAdvisorRepository = CAdvisorRepository.getInstance();
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_container_list);

        setAppContext(getApplicationContext());

        createContainerListRecyclerView();
        createSwipeToRefreshListener();
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
        fetchData();
        super.onResume();
    }

    private void createContainerListRecyclerView() {
        containerListRecyclerView = (RecyclerView) findViewById(R.id.container_list_recyclerview);

        containerListLayoutManager = new LinearLayoutManager(this);

        List<ContainerModel> containers = new ArrayList<>();
        containerListRecyclerAdapter = new ContainerListRecyclerViewAdapter(containers, this);

        containerListRecyclerView.setHasFixedSize(true);

        containerListRecyclerView.setLayoutManager(containerListLayoutManager);

        containerListRecyclerView.setAdapter(containerListRecyclerAdapter);
    }

    private void createSwipeToRefreshListener() {
        final SwipeRefreshLayout swiperefreshContainerListRecyclerView = (SwipeRefreshLayout) this.findViewById(R.id.swiperefresh_container_list_recyclerview);

        swiperefreshContainerListRecyclerView.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshContainerList();
                    }
                }
        );
    }

    public void refreshContainerList(){
        swiperefreshContainerListRecyclerView = (SwipeRefreshLayout) this.findViewById(R.id.swiperefresh_container_list_recyclerview);
        refreshData();
    }

    private void refreshData() {
        cAdvisorRepository.refreshData(new ICAdvisorRepository.GetContainersCallback() {
            @Override
            public void onContainersLoaded(Map containers) {
                renderContainersIntoRecyclerView(containers);
                swiperefreshContainerListRecyclerView.setRefreshing(false);
            }
        });
    }

    private void fetchData() {
        cAdvisorRepository.getContainers(new ICAdvisorRepository.GetContainersCallback() {
            @Override
            public void onContainersLoaded(Map containers) {
                renderContainersIntoRecyclerView(containers);
            }
        });
    }

    private void renderContainersIntoRecyclerView(Map containers) {
        containerListRecyclerAdapter.clear();

        Iterator<Map.Entry<String, ContainerModel>> iterator = containers.entrySet().iterator();
        int position = 0;

        while(iterator.hasNext()){
            containerListRecyclerAdapter.addItem(position, iterator.next().getValue());
        }

        position++;
    }

    public static Context getAppContext() {
        return context;
    }

    public void setAppContext(Context mAppContext) {
        this.context = mAppContext;
    }
}
