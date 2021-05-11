package com.example.pokedexunifaesp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
/*

public class pokeConsult extends AsyncTask<String, Void, String> {
 */
/*   private ProgressDialog progressDialog;

    //Classe estendendo AsyncTask para realizar a consulta do Cep em segundo plano. Uma classe Java pode ser criado dentro de outra sem problemas
        @Override
        protected String doInBackground(String... strings) {
            try {
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
                return
                        out.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String dados) {
            try {
                parseJSON(dados);
                progressDialog.hide();
            }catch (Exception e){
                progressDialog.hide();
            }
        }

        //O método abaixo serve para ler o JSON recebido e processar os itens contidos no mesmo
        private void parseJSON(String data){
            try{
                if(data.contains("erro")){
                    Toast.makeText(MainActivity.this, "Falha na consulta", Toast.LENGTH_SHORT).show();
                }else {
                    JSONObject jsonObject = new JSONObject(data);//JSONObject recebe o JSON da consulta
                    text.set
                    //Depois de pegar o conteúdo no JSON, preenche os textviews
                    textViewEndereco.setText(jsonObject.getString("logradouro"));
                    textViewCidade.setText(jsonObject.getString("localidade"));
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }*//*

}
*/
