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
import com.uk.jacob.containerdroid.presenters.ContainerListActivityPresenter;
import com.uk.jacob.containerdroid.presenters.interfaces.IContainerListActivityPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ContainerListActivity extends ActionBarActivity {
    private RecyclerView containerListRecyclerView;
    private static ContainerListRecyclerViewAdapter containerListRecyclerAdapter;

    private SwipeRefreshLayout swiperefreshContainerListRecyclerView;

    private ContainerListActivityPresenter containerListActivityPresenter = new ContainerListActivityPresenter();

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
        containerListActivityPresenter.fetchData(new IContainerListActivityPresenter.Callback() {
            @Override
            public void callback(Map containers) {
                renderContainersIntoRecyclerView(containers);
            }
        });

        super.onResume();
    }

    private void createContainerListRecyclerView() {
        containerListRecyclerView = (RecyclerView) findViewById(R.id.container_list_recyclerview);

        containerListRecyclerAdapter = new ContainerListRecyclerViewAdapter(new ArrayList<ContainerModel>(), this);

        containerListRecyclerView.setHasFixedSize(true);
        containerListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        containerListActivityPresenter.refreshData(new IContainerListActivityPresenter.Callback() {
            @Override
            public void callback(Map containers) {
                renderContainersIntoRecyclerView(containers);
                swiperefreshContainerListRecyclerView.setRefreshing(false);
            }
        });
    }

    public void renderContainersIntoRecyclerView(Map containers) {
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
