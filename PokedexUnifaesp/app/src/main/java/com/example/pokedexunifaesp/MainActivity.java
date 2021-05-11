package com.example.pokedexunifaesp;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    private Button buttonSearch;
    private EditText editPokeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPokeName = (EditText) findViewById(R.id.editPokeName);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPokemon searchPokemon = new searchPokemon();
                searchPokemon.execute("https://pokeapi.co/api/v2/pokemon/" + editPokeName.getText());
            }
        });
    }

    //Classe estendendo AsyncTask para realizar a consulta do Cep em segundo plano. Uma classe Java pode ser criado dentro de outra sem problemas
    protected class searchPokemon extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                System.out.println(strings[0]);
                URL url = new URL(strings[0]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            //Este método é acionado após doInBackground acessar o serviço web e baixar os dados da consulta
            try {
                startResultActivity(parseJSON(result));
            }catch (Exception e){
                System.out.println(e);
            }
        }

        private void startResultActivity(JSONObject resultJson){
            try{
            Intent intentResult = new Intent(MainActivity.this, pokemonData.class);

            String pokeName = resultJson.getString("name");
                intentResult.putExtra("pokeName", pokeName);

            String pokeType = resultJson.getJSONArray("types")
                    .getJSONObject(0)
                    .getJSONObject("type")
                    .getString("name");
                intentResult.putExtra("pokeType", pokeType);

            String pokeSprite = resultJson.getJSONObject("sprites")
                    .getJSONObject("other")
                    .getJSONObject("official-artwork")
                    .getString("front_default");
                intentResult.putExtra("pokeSprite", pokeSprite);

            startActivity(intentResult);
            } catch(Exception e) {
                Intent intentError = new Intent(MainActivity.this, errorSearch.class);
                startActivity(intentError);
            }
        }

        //O método abaixo serve para ler o JSON recebido e processar os itens contidos no mesmo
        private JSONObject parseJSON(String data){
            try{
                //JSONObject recebe o JSON da consulta
                JSONObject jsonObject = new JSONObject(data);

                return jsonObject;
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                return null;
            }
        }
    }
}