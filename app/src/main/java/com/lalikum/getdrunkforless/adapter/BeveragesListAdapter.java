package com.lalikum.getdrunkforless.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
        // TODO create empty container at the end
        // TODO set list limit max count to prevent memory overflow
        Beverage beverage = beverageList.get(position);
        // TODO show beverage top list numbers and pony heads (gold, silver, bronze)
        // TODO show beverage type (wine beer short)
        holder.beverageNameTextView.setText(beverage.getName());
        // TODO add a new text if the item was added now
        // TODO scroll to the newly added item after save or edit
        holder.sizeTextView.setText(beverageController.getSizeWithSuffix(beverage));
        holder.alcoholByVolumeTextView.setText(beverageController.getAlcoholByVolumeWithSuffix(beverage));
        holder.priceTextView.setText(beverageController.getPriceWithSuffix(beverage));
        holder.bottlesTextView.setText(beverageController.getBottlesWithSuffix(beverage));
        holder.alcoholValueTextView.setText(beverageController.getAlcoholValueWithSuffix(beverage));
        // set valueBar
        if (position == 0) {
            holder.valueBar.setProgress(100);
        } else {
            holder.valueBar.setProgress((int) (beverageList.get(0).getAlcoholValue() /  beverageList.get(position).getAlcoholValue() * 100));
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
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void removeItem(int position) {
        beverageList.remove(position);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView beverageNameTextView;
        TextView sizeTextView;
        TextView alcoholByVolumeTextView;
        TextView priceTextView;
        TextView bottlesTextView;
        TextView alcoholValueTextView;
        ProgressBar valueBar;

        ViewHolder(View itemView) {
            super(itemView);
            beverageNameTextView = itemView.findViewById(R.id.beverageNameLayoutTextView);
            sizeTextView = itemView.findViewById(R.id.sizeLayoutTextView);
            alcoholByVolumeTextView = itemView.findViewById(R.id.alcoholByVolumeLayoutTextView);
            priceTextView = itemView.findViewById(R.id.priceLayoutTextView);
            bottlesTextView = itemView.findViewById(R.id.bottlesLayoutTextView);
            alcoholValueTextView = itemView.findViewById(R.id.alcoholValueLayoutTextView);
            valueBar = itemView.findViewById(R.id.valueBar);

//            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            // TODO edit with long press animation
            System.out.println("Looooooooooooooooooooooooong press edit mode");
            Context context = layoutInflater.getContext();
            Beverage beverage = getItem(getAdapterPosition());
            Intent intent = new Intent(context, AddBeverageActivity.class);
            intent.putExtra("beverageId", beverage.getId());
            context.startActivity(intent);
            return false;
        }
    }

}
