package com.christopher.pokemonuisample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.christopher.pokemonuisample.Common.Common;
import com.christopher.pokemonuisample.Model.Pokemon;

public class MainActivity extends AppCompatActivity {

    Toolbar pokeToolbar;

    BroadcastReceiver showDetails = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Common.KEY_ENABLE_HOME)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

                //Replace Fragment
                Fragment detailFragment = PokemonDetails.getInstance();
                int position = intent.getIntExtra("position", -1);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment);
                fragmentTransaction.addToBackStack("detail");
                fragmentTransaction.commit();

                //Sets Pokemon name for Toolbar.
                Pokemon pokemon = Common.commonPokemonList.get(position);
                pokeToolbar.setTitle(pokemon.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokeToolbar = findViewById(R.id.toolbar);
        pokeToolbar.setTitle("3•Sided•Cube");
        pokeToolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(pokeToolbar);

        //Registers broadcast.
        LocalBroadcastManager.getInstance(this).registerReceiver(showDetails, new IntentFilter(Common.KEY_ENABLE_HOME));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            pokeToolbar.setTitle("Pokemon List");
            //Clear all fragment details and pop to list fragment.
            getSupportFragmentManager().popBackStack("details", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return true;
    }
}