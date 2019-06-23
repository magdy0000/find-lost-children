package com.example.findlostchildren.Constrollers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findlostchildren.Constrollers.Holders.VictimHolder;
import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import com.squareup.picasso.Picasso;
import java.util.List;


public class VictimAdapter extends RecyclerView.Adapter<VictimHolder> {

    private Context context;
    private List<VictimModel> victimModels;

    public VictimAdapter(Context context, List<VictimModel> victimModels) {
        this.context = context;
        this.victimModels = victimModels;
    }

    @NonNull
    @Override
    public VictimHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.victim_item, parent, false);
        return new VictimHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VictimHolder holder, int position) {
        VictimModel victimModel  = victimModels.get(position);
        holder.sourceName.setText(victimModel.getSourceName());
        holder.postTime.setText(victimModel.getPostTime());
        holder.victimDescription.setText(victimModel.getDescription());
        holder.victimName.setText(victimModel.getName());
        holder.detalisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Details Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return victimModels.size();
    }
}