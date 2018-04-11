package com.lalikum.getdrunkforless.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lalikum.getdrunkforless.R;
import com.lalikum.getdrunkforless.controller.BeverageController;
import com.lalikum.getdrunkforless.model.Beverage;

import java.util.List;

public class BeveragesListAdapter extends RecyclerView.Adapter<BeveragesListAdapter.ViewHolder> {

    private BeverageController beverageController = new BeverageController();

    private List<Beverage> beverageList;
    private LayoutInflater layoutInflater;
    private ItemClickListener itemClickListener;

    // data is passed into the constructor
    public BeveragesListAdapter(Context context, List<Beverage> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.beverageList = data;
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
        Beverage beverage = beverageList.get(position);
        holder.beverageNameTextView.setText(beverage.getName());
        holder.sizeTextView.setText(beverageController.getSizeWithSuffix(beverage));
        holder.alcoholByVolumeTextView.setText(beverageController.getAlcoholByVolumeWithSuffix(beverage));
        holder.priceTextView.setText(beverageController.getPriceWithSuffix(beverage));
        holder.bottlesTextView.setText(beverageController.getBottlesWithSuffix(beverage));
        holder.alcoholValueTextView.setText(beverageController.getAlcoholValueWithSuffix(beverage));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return beverageList.size();
    }

    // convenience method for getting data at click position
    public Beverage getItem(int id) {
        return beverageList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView beverageNameTextView;
        TextView sizeTextView;
        TextView alcoholByVolumeTextView;
        TextView priceTextView;
        TextView bottlesTextView;
        TextView alcoholValueTextView;

        ViewHolder(View itemView) {
            super(itemView);
            beverageNameTextView = itemView.findViewById(R.id.beverageNameLayoutTextView);
            sizeTextView = itemView.findViewById(R.id.sizeLayoutTextView);
            alcoholByVolumeTextView = itemView.findViewById(R.id.alcoholByVolumeLayoutTextView);
            priceTextView = itemView.findViewById(R.id.priceLayoutTextView);
            bottlesTextView = itemView.findViewById(R.id.bottlesLayoutTextView);
            alcoholValueTextView = itemView.findViewById(R.id.alcoholValueLayoutTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
