package com.lalikum.getdrunkforless.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lalikum.getdrunkforless.AddBeverageActivity;
import com.lalikum.getdrunkforless.R;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.model.Beverage;

import java.util.List;

public class BeveragesListAdapter extends RecyclerView.Adapter<BeveragesListAdapter.ViewHolder> {

    private BeverageController beverageController = new BeverageController();

    private List<Beverage> beverageList;
    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;
    private Context context;

    // data is passed into the constructor
    public BeveragesListAdapter(Context context, List<Beverage> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        // set null for an empty last row
        this.beverageList = data;
        beverageList.add(null);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_beverages_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            // make an empty holder in last place
            holder.itemView.setOnClickListener(null);
            holder.itemView.setBackground(null);
            holder.beverageNameTextView.setVisibility(View.GONE);
            holder.alcoholValueTextView.setVisibility(View.GONE);
            holder.medalImageView.setVisibility(View.GONE);
            holder.valueBar.setVisibility(View.GONE);
            return;
        }
        Beverage beverage = beverageList.get(position);
        holder.beverageNameTextView.setText(beverage.getName());
        // TODO add a new text if the item was added now
        holder.alcoholValueTextView.setText(beverageController.getAlcoholValueWithUnit(beverage) + " " + context.getString(R.string.home_of_alcohol_suffix));
        // set valueBar
        if (position == 0) {
            holder.valueBar.setProgress(100);
        } else {
            holder.valueBar.setProgress((int) (beverageList.get(0).getAlcoholValue() / beverageList.get(position).getAlcoholValue() * 100));
        }

        // set medal image
        switch (position) {
            case 0:
                holder.medalImageView.setImageResource(R.drawable.iw_gold_head);
                break;
            case 1:
                holder.medalImageView.setImageResource(R.drawable.iw_silver_head);
                break;
            case 2:
                holder.medalImageView.setImageResource(R.drawable.iw_bronze_head);
                break;
            default:
                holder.medalImageView.setImageResource(R.drawable.iw_sad_head);
                break;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return beverageList.size();
    }

    // convenience method for getting data at click position
    public Beverage getItem(int position) {
        return beverageList.get(position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void removeItem(int position) {
        beverageList.remove(position);
        this.notifyDataSetChanged();
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView beverageNameTextView;
        TextView alcoholValueTextView;
        ProgressBar valueBar;
        ImageView medalImageView;
        public RelativeLayout backgroundRelativeLayout;
        public ConstraintLayout foregroundConstraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            beverageNameTextView = itemView.findViewById(R.id.tvHomeBeverageName);
            alcoholValueTextView = itemView.findViewById(R.id.tvHomeAlcoholValue);
            valueBar = itemView.findViewById(R.id.vbHomeAlcoholValueBar);
            medalImageView = itemView.findViewById(R.id.ivHomeMedal);
            backgroundRelativeLayout = itemView.findViewById(R.id.rlHomeBackground);
            foregroundConstraintLayout = itemView.findViewById(R.id.clHomeForeground);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = layoutInflater.getContext();
            Beverage beverage = getItem(getAdapterPosition());
            Intent intent = new Intent(context, AddBeverageActivity.class);
            intent.putExtra("beverageId", beverage.getId());
            context.startActivity(intent);
        }
    }

}
