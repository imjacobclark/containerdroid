package com.uk.jacob.containerdroid.activities.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.activities.ContainerListActivity;
import com.uk.jacob.containerdroid.models.ContainerModel;
import com.uk.jacob.containerdroid.services.CAdvisorService;

import java.util.List;

public class ContainerListRecyclerViewAdapter extends RecyclerView.Adapter<ContainerListRecyclerViewAdapter.ContainerListViewHolder>{
    private final ContainerListActivity containerListActivity;
    private boolean isRefreshing = false;
    private List<ContainerModel> containers;
    private CAdvisorService cAdvisorService;

    public static class ContainerListViewHolder extends RecyclerView.ViewHolder {
        TextView containerName;
        TextView containerNamespace;

        ContainerListViewHolder(View itemView) {
            super(itemView);
            containerName = (TextView)itemView.findViewById(R.id.container_name);
            containerNamespace = (TextView)itemView.findViewById(R.id.container_namespace);
        }
    }


    public ContainerListRecyclerViewAdapter(List<ContainerModel> containers, ContainerListActivity containerListActivity){
        this.containers = containers;
        this.containerListActivity = containerListActivity;
        this.cAdvisorService = new CAdvisorService();
    }

    @Override
    public ContainerListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.container_list_item, viewGroup, false);
        return new ContainerListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContainerListViewHolder containerListViewHolder, final int i) {
        containerListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(containerListActivity, com.uk.jacob.containerdroid.activities.ContainerDetailsActivity.class);

                try {
                    intent.putExtra("container_specs", cAdvisorService.mapObjectToJsonString(containers.get(i).getSpec()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                containerListActivity.startActivity(intent);
            }
        });

        containerListViewHolder.containerName.setText(containers.get(i).getAliases());
        containerListViewHolder.containerNamespace.setText(containers.get(i).getSpec().getImage());
        System.out.println(containers.get(i).getSpec().getCreated());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public void addItem(int position, ContainerModel data) {
        containers.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        containers.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        containers.clear();
        notifyDataSetChanged();
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }

}