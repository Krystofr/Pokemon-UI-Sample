package com.christopher.pokemonuisample.Retrofit;

import com.christopher.pokemonuisample.Model.Pokedex;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PokemonDex {

    @GET("pokedex.json")
    Observable<Pokedex> getListPokemon();
}
