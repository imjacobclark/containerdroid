package com.uk.jacob.containerdroid.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.activities.ContainerDetails;
import com.uk.jacob.containerdroid.activities.MainActivity;
import com.uk.jacob.containerdroid.models.Container;

import java.io.Serializable;
import java.util.List;

public class ContainerListRecyclerViewAdapter extends RecyclerView.Adapter<ContainerListRecyclerViewAdapter.ContainerListViewHolder>{
    private final MainActivity mainActivity;
    public boolean isRefreshing = false;

    public static class ContainerListViewHolder extends RecyclerView.ViewHolder {
        TextView containerName;
        TextView containerNamespace;

        ContainerListViewHolder(View itemView) {
            super(itemView);
            containerName = (TextView)itemView.findViewById(R.id.container_name);
            containerNamespace = (TextView)itemView.findViewById(R.id.container_namespace);
        }
    }

    List<Container> containers;

    public ContainerListRecyclerViewAdapter(List<Container> containers, MainActivity mainActivity){
        this.containers = containers;
        this.mainActivity = mainActivity;
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
                Intent intent = new Intent(mainActivity, com.uk.jacob.containerdroid.activities.ContainerDetails.class);
                intent.putExtra("containerAlias", containers.get(i).getAliases());
                mainActivity.startActivity(intent);
            }
        });

        containerListViewHolder.containerName.setText(containers.get(i).getAliases());
        containerListViewHolder.containerNamespace.setText(containers.get(i).getSpec().getImage());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    public void addItem(int position, Container data) {
        containers.add(position, data);
        notifyItemInserted(position);
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