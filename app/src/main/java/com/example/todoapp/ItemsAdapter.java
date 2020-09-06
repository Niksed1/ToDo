package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//displaying data from model in a row of the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //creating each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflater to inflate a view
        //wrap inside a View Holder

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    //data over position
    //responsible for binding data to a particular view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //grab item at a pos
        String item = items.get(position);
        //bind item into spec view holder
        holder.bind(item);
    }

    @Override
    //tells RV num items abailable on list
    public int getItemCount() {
        return items.size();
    }

    //Container to represent each item of a row in a list
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    // Update the view inside of the view holder with data
        public void bind(String item) {
            textView.setText(item);
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Notify the listener which position was pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
            {

            }
        }
    }
}
