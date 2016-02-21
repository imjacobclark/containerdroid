package com.uk.jacob.containerdroid.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.activities.ContainerListActivity;
import com.uk.jacob.containerdroid.models.ContainerModel;

import java.util.List;

public class ContainerListRecyclerViewAdapter extends RecyclerView.Adapter<ContainerListRecyclerViewAdapter.ContainerListViewHolder>{
    private final ContainerListActivity containerListActivity;
    private List<ContainerModel> containers;

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
                intent.putExtra("container_alias", containers.get(i).getAliasId());
                containerListActivity.startActivity(intent);
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
}