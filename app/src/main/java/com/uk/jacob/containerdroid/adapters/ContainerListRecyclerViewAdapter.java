package com.uk.jacob.containerdroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uk.jacob.containerdroid.R;
import com.uk.jacob.containerdroid.models.Container;

import java.util.List;

public class ContainerListRecyclerViewAdapter extends RecyclerView.Adapter<ContainerListRecyclerViewAdapter.ContainerListViewHolder>{

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

    public ContainerListRecyclerViewAdapter(List<Container> containers){
        this.containers = containers;
    }

    @Override
    public ContainerListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.container_list_item, viewGroup, false);
        return new ContainerListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContainerListViewHolder personViewHolder, final int i) {
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Recycle Click" + i);
            }
        });

        personViewHolder.containerName.setText(containers.get(i).getAliases());
        personViewHolder.containerNamespace.setText(containers.get(i).getNamespace());
        personViewHolder.containerNamespace.append(" | ");
        personViewHolder.containerNamespace.append(containers.get(i).getSpec().getImage());
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

}