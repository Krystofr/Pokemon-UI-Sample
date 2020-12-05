package com.christopher.pokemonuisample.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.christopher.pokemonuisample.Common.Common;
import com.christopher.pokemonuisample.Interface.ItemClickListener;
import com.christopher.pokemonuisample.Model.Pokemon;
import com.christopher.pokemonuisample.R;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> pokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View pokeItemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item, parent, false);
        return new MyViewHolder(pokeItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Loads pokemon image
        Glide.with(context)
                .load(pokemonList.get(position)
                        .getImg()).into(holder.pokemonImage);
        //Set pokemon name
        holder.pokemonName.setText(pokemonList.get(position).getName());

        //Click Event
        holder.setItemClickListener((view, position1) -> {
            //Toast.makeText(context, "You clicked" +pokemonList.get(position1).getName(), Toast.LENGTH_LONG).show();
            LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("position", position1));
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemonImage;
        TextView pokemonName;
        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemonImage = itemView.findViewById(R.id.poke_image);
            pokemonName = itemView.findViewById(R.id.txt_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
