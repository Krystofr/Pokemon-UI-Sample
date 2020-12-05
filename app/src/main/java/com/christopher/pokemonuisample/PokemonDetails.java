package com.christopher.pokemonuisample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.christopher.pokemonuisample.Common.Common;
import com.christopher.pokemonuisample.Model.Pokemon;

public class PokemonDetails extends Fragment {

    ImageView pokemon_img;
    TextView pokemon_name, pokemon_height, pokemon_weight;
    RecyclerView recycler_type, recycler_weakness, recycler_prev_evolution, recycler_next_evolution;

    static PokemonDetails instance;

    public static PokemonDetails getInstance(){
        if (instance == null)
            instance = new PokemonDetails();
        return instance;
    }

    public PokemonDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_pokemon_details, container, false);

        Pokemon pokemon;
        //Get position from argument
        if (getArguments().get("num") == null)
            pokemon = Common.commonPokemonList.get(getArguments().getInt("position"));
        else
            pokemon = null;

        pokemon_img = itemView.findViewWithTag(R.id.pokemon_image);
        pokemon_name = itemView.findViewWithTag(R.id.name);
        pokemon_height = itemView.findViewWithTag(R.id.height);
        pokemon_weight = itemView.findViewWithTag(R.id.weight);

        recycler_type = itemView.findViewWithTag(R.id.recycler_type);
        recycler_type.setHasFixedSize(true);
        recycler_type.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_weakness = itemView.findViewWithTag(R.id.recycler_weakness);
        recycler_weakness.setHasFixedSize(true);
        recycler_weakness.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_prev_evolution = itemView.findViewWithTag(R.id.recycler_prev_evolution);
        recycler_prev_evolution.setHasFixedSize(true);
        recycler_prev_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycler_next_evolution = itemView.findViewWithTag(R.id.recycler_next_evolution);
        recycler_prev_evolution.setHasFixedSize(true);
        recycler_next_evolution.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        setPokemonDetails(pokemon);

        return itemView;
    }

    private void setPokemonDetails(Pokemon pokemon) {
        //Load image
        Glide.with(getActivity()).load(pokemon.getImg()).into(pokemon_img);

        pokemon_name.setText(pokemon.getName());
        pokemon_height.setText("Height: " +pokemon.getHeight());
        pokemon_weight.setText("Weight: " +pokemon.getWeight());
    }
}