package com.christopher.pokemonuisample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christopher.pokemonuisample.Adapter.PokemonListAdapter;
import com.christopher.pokemonuisample.Common.Common;
import com.christopher.pokemonuisample.Common.ItemOffsetDecoration;
import com.christopher.pokemonuisample.Model.Pokedex;
import com.christopher.pokemonuisample.Model.Pokemon;
import com.christopher.pokemonuisample.Retrofit.PokemonDex;
import com.christopher.pokemonuisample.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PokemonList extends Fragment {

    PokemonDex pokemonDex;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView pokemon_list_recyclerview;

    static PokemonList instance;

    public static PokemonList getInstance() {
        if (instance == null)
            instance = new PokemonList();
        return instance;
    }

    public PokemonList() {
        Retrofit retrofit = RetrofitClient.getInstance();
        pokemonDex = retrofit.create(PokemonDex.class);
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pokemon_list, container, false);

        pokemon_list_recyclerview = view.findViewById(R.id.pokemon_list_recyclerview);
        pokemon_list_recyclerview.setHasFixedSize(true);
        pokemon_list_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.spacing);
        pokemon_list_recyclerview.addItemDecoration(itemOffsetDecoration);

        fetchData();
        return view;
    }

    private void fetchData() {
        compositeDisposable.add(pokemonDex.getListPokemon()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pokedex -> {
                    Common.commonPokemonList = pokedex.getPokemon();
                    PokemonListAdapter pokemonListAdapter = new PokemonListAdapter(getActivity(), Common.commonPokemonList);

                    pokemon_list_recyclerview.setAdapter(pokemonListAdapter);
                })
        );
    }


}