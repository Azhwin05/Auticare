package com.example.auticare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    private Context context;
    private List<Community> communityList;

    public CommunityAdapter(Context context, List<Community> communityList) {
        this.context = context;
        this.communityList = communityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Community community = communityList.get(position);
        holder.communityName.setText(community.getName());
        holder.communityDescription.setText(community.getDescription());

        // Set image based on community name
        switch (community.getName()) {
            case "Doctor Help":
                holder.communityIcon.setImageResource(R.drawable.ic_doctor);
                break;
            case "Education":
                holder.communityIcon.setImageResource(R.drawable.ic_education);
                break;
            case "Support Groups":
                holder.communityIcon.setImageResource(R.drawable.ic_support);
                break;
            case "Events":
                holder.communityIcon.setImageResource(R.drawable.ic_event);
                break;
            case "Awareness":
                holder.communityIcon.setImageResource(R.drawable.ic_awarness);
                break;
            default:
                holder.communityIcon.setImageResource(R.drawable.ic_community); // default fallback
                break;
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommunityChatActivity.class);
            intent.putExtra("communityName", community.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView communityName, communityDescription;
        ImageView communityIcon;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            communityName = itemView.findViewById(R.id.communityName);
            communityDescription = itemView.findViewById(R.id.communityDescription);
            communityIcon = itemView.findViewById(R.id.communityIcon);
            cardView = (CardView) itemView;
        }
    }
}
