package com.example.pokedexunifaesp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class pokemonData extends AppCompatActivity {
        private TextView textPokeName;
        private TextView textPokeType;
        private ImageView imagePoke;
        private Bitmap bitmap;
        private Bundle bundle;
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.out.println("Comecou");
            setContentView(R.layout.pokemon_data);

            RetrieveImage retrieve = new RetrieveImage();
            retrieve.execute("string");
        }

    class RetrieveImage extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                bundle = getIntent().getExtras();
                URL pokeUrl;
                try {
                    pokeUrl = new URL(bundle.getString("pokeSprite"));

                    bitmap = null;
                    bitmap = BitmapFactory.decodeStream(pokeUrl.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(pokemonData.this);
            progressDialog.setMessage("Aguarde, carregando dados...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String data) {
                String pokeName = bundle.getString("pokeName");
                String pokeType = bundle.getString("pokeType");

                if (pokeType != null && pokeName != null) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textPokeName = (TextView) findViewById(R.id.textSearchPoke);
                            textPokeType = (TextView) findViewById(R.id.textPokeType);
                            textPokeName.setText(pokeName.toUpperCase());

                            textPokeType.setText(pokeType.toUpperCase());
                            imagePoke = (ImageView) findViewById(R.id.viewPoke);
                            imagePoke.setImageBitmap(bitmap);

                            progressDialog.hide();
                        }
                    });
                }
        }
    }

}

