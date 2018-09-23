package com.example.suyash.graph;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by karthik on 9/21/18.
 */

public class BarGraphEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<BarGraphEntryModel> list;

    public BarGraphEntryAdapter(Context context, ArrayList<BarGraphEntryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bargraph_new_entries, parent, false);
        return new BarGraphHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        BarGraphEntryModel model = list.get(position);
        if (model != null) {
            ((BarGraphHolder) holder).name.setText(model.getName());
            ((BarGraphHolder) holder).data.setText(Float.toString(model.getData()));
            ((BarGraphHolder) holder).color.setBackgroundColor(model.getColor());
        }
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            if (list.size() == 0) {
                arr = 0;
            } else {
                arr = list.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    class BarGraphHolder extends RecyclerView.ViewHolder {
        TextView name, data;
        ImageView color;
        ImageButton menu;
        Context context;

        public BarGraphHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.barGraphEntriesName);
            data = itemView.findViewById(R.id.barGraphEntriesData);
            color = itemView.findViewById(R.id.barGraphEntriesColor);
            menu = itemView.findViewById(R.id.barGraphEntriesMenu);

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popupMenu = new PopupMenu(context, menu);
                    popupMenu.inflate(R.menu.bar_graph_entries_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit:
                                    Intent intent = new Intent(itemView.getContext(), BarPointNew.class);
                                    intent.putExtra("editName", list.get(getAdapterPosition()).getName());
                                    intent.putExtra("editData", list.get(getAdapterPosition()).getData());
                                    intent.putExtra("editColor", list.get(getAdapterPosition()).getColor());
                                    list.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    ((Activity)context).finish();
                                    itemView.getContext().startActivity(intent);
                                    break;
                                case R.id.delete:
                                    list.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }
}