package com.example.pokedexunifaesp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class PokemonData extends AppCompatActivity {
        private TextView textPokeName;
        private TextView textPokeType;
        private TextView textPokeDesc;
        private TextView textDefense;
        private TextView textHP;
        private TextView textSpeed;
        private TextView textAttack;
        private ImageView imagePoke;

        private Bitmap bitmap;
        private Bundle bundle;
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.pokemon_data);

            RetrieveImage retrieve = new RetrieveImage();
            retrieve.execute("string");
        }

    class RetrieveImage extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                bundle = getIntent().getExtras();
                URL pokeUrl = null;

                try {
                    pokeUrl = new URL(bundle.getString("pokeURL"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    bitmap = null;
                    bitmap = BitmapFactory.decodeStream(pokeUrl.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PokemonData.this);
            progressDialog.setMessage("Aguarde um instante enquanto os dados estão sendo carregados...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String data) {
                String pokeName     = bundle.getString("pokeName").substring(0, 1).toUpperCase() + bundle.getString("pokeName").substring(1);
                String pokeType     = "Tipo: " + bundle.getString("pokeType").substring(0, 1).toUpperCase() + bundle.getString("pokeType").substring(1);
                String pokeDesc     = bundle.getString("pokeDesc").replace("\n", " ");
                String pokeAtk      = bundle.getString("pokeAtk");
                String pokeDef      = bundle.getString("pokeDef");
                String pokeHp       = bundle.getString("pokeHp");
                String pokeSpeed    = bundle.getString("pokeSpeed");

                if (pokeType != null && pokeName != null) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            textPokeName    = (TextView) findViewById(R.id.textSearchPoke);
                            textPokeType    = (TextView) findViewById(R.id.textPokeType);
                            textPokeDesc    = (TextView) findViewById(R.id.textPokeDesc);
                            textDefense     = (TextView) findViewById(R.id.textDefense);
                            textDefense     = (TextView) findViewById(R.id.textDefense);
                            textHP          = (TextView) findViewById(R.id.textHP);
                            textSpeed       = (TextView) findViewById(R.id.textSpeed);
                            textAttack      = (TextView) findViewById(R.id.textAttack);

                            textPokeName.setText(pokeName);
                            textPokeType.setText(pokeType);
                            textPokeDesc.setText(pokeDesc);
                            textDefense.setText(pokeDef);
                            textHP.setText(pokeHp);
                            textSpeed.setText(pokeSpeed);
                            textAttack.setText(pokeAtk);

                            imagePoke = (ImageView) findViewById(R.id.viewPoke);
                            imagePoke.setImageBitmap(bitmap);
                            progressDialog.hide();

                        }
                    });

                }
        }

    }

}

