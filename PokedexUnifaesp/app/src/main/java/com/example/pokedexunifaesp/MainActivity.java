package com.example.pokedexunifaesp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText) findViewById(R.id.editPokeName);
        button = (Button) findViewById(R.id.buttonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPokemon searchPoke = new SearchPokemon();
                searchPoke.execute("https://pokeapi.co/api/v2/pokemon/" + edit.getText());
            }
        });
    }

    protected class SearchPokemon extends AsyncTask<String, Void, String> {
        private URL requestURL;
        private String pokeName;
        private String pokeType;
        private String pokeAtk;
        private String pokeDef;
        private String pokeHp;
        private String pokeSpeed;
        private String pokeURL;

        @Override
        protected String doInBackground(String... strings) {
            try {
                this.requestURL = new URL(strings[0]);

                HttpURLConnection con = (HttpURLConnection) this.requestURL.openConnection();
                con.setRequestMethod("GET");

                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                StringBuffer out = new StringBuffer();

                while ((line = br.readLine()) != null) {
                    out.append(line + "\n");
                }

                is.close();

                return out.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = parseJSON(result);

                if (this.requestURL.toString().contains("https://pokeapi.co/api/v2/pokemon-species/")) {
                    Intent intent = new Intent(MainActivity.this, PokemonData.class);

                    intent.putExtra("pokeName", this.pokeName);
                    intent.putExtra("pokeType", this.pokeType);
                    intent.putExtra("pokeAtk", this.pokeAtk);
                    intent.putExtra("pokeDef", this.pokeDef);
                    intent.putExtra("pokeHp", this.pokeHp);
                    intent.putExtra("pokeSpeed", this.pokeSpeed);
                    intent.putExtra("pokeURL", this.pokeURL);
                    intent.putExtra("pokeDesc", json.getJSONArray("flavor_text_entries").getJSONObject(1).getString("flavor_text"));

                    startActivity(intent);
                } else {
                    SearchPokemon poke = new SearchPokemon();

                    poke.pokeName = json.getString("name");
                    poke.pokeType = json.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name");
                    poke.pokeHp = json.getJSONArray("stats").getJSONObject(0).getString("base_stat");
                    poke.pokeAtk = json.getJSONArray("stats").getJSONObject(1).getString("base_stat");
                    poke.pokeDef = json.getJSONArray("stats").getJSONObject(2).getString("base_stat");
                    poke.pokeSpeed = json.getJSONArray("stats").getJSONObject(5).getString("base_stat");
                    poke.pokeURL = json.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default");

                    poke.execute("https://pokeapi.co/api/v2/pokemon-species/" + poke.pokeName);
                }
            } catch (Exception e) {
                Intent intent = new Intent(MainActivity.this, ErrorSearch.class);
                startActivity(intent);
            }
        }

        //O método abaixo serve para ler o JSON recebido e processar os itens contidos no mesmo
        private JSONObject parseJSON(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);

                return jsonObject;
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                return null;
            }
        }
    }
}