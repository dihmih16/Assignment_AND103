package com.example.assignment_and103.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment_and103.R;
import com.example.assignment_and103.databinding.ItemFruitUserBinding;
import com.example.assignment_and103.model.Fruit;
import com.example.assignment_and103.model.Page;
import com.example.assignment_and103.model.Response;

import java.util.ArrayList;

import retrofit2.Callback;

public class Fruit_User_Adapter extends RecyclerView.Adapter<Fruit_User_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Fruit> list;
    private FruitClick fruitClick;

    public Fruit_User_Adapter( ArrayList<Fruit> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public interface FruitClick {
        void delete(Fruit fruit);
        void edit(Fruit fruit);

        void showDetail(Fruit fruit);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitUserBinding binding = ItemFruitUserBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.binding.tvNameFruit.setText(fruit.getName());
        holder.binding.tvPrice.setText(fruit.getPrice()+ " VND");
        String url  = fruit.getImage().get(0);
        String newUrl = url.replace("localhost", "10.0.2.2");
        Glide.with(context)
                .load(newUrl)
                .thumbnail(Glide.with(context).load(R.drawable.baseline_broken_image_24))
                .into(holder.binding.imgFruit);

//        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fruitClick.edit(fruit);
//            }
//        });
//        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fruitClick.delete(fruit);
//            }
//        });

        Log.d("321321", "onBindViewHolder: "+list.get(position).getImage().get(0));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFruitUserBinding binding;
        public ViewHolder(ItemFruitUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
