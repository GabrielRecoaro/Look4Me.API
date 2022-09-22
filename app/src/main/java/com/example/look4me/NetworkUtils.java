package com.example.look4me;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String API_URL = "https://localhost93648/api/Sobrenome/getAllSobrenomes";

    private static final String QUERY_PARAM = "q";

    private static final String MAX_RESULTS = "maxResults";

    private static final String TIPO_IMPRESSAO = "printType";

    static String buscaInfosSobrenomes(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String sobrenomeJSONString = null;
        try {

            Uri builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "15")
                    .appendQueryParameter(TIPO_IMPRESSAO, "sobrenomes")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {

                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {

                return null;
            }
            sobrenomeJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, sobrenomeJSONString);
        return sobrenomeJSONString;
    }
    static String buscaInfosNome(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String nomeJSONString = null;
        try {

            Uri builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "15")
                    .appendQueryParameter(TIPO_IMPRESSAO, "nomes")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {

                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {

                return null;
            }
            nomeJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, nomeJSONString);
        return nomeJSONString;
    }

    static String buscaInfosPais(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String paisJSONString = null;
        try {

            Uri builtURI = Uri.parse(API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "15")
                    .appendQueryParameter(TIPO_IMPRESSAO, "pais")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {

                builder.append(linha);
                builder.append("\n");
            }
            if (builder.length() == 0) {

                return null;
            }
            paisJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, paisJSONString);
        return paisJSONString;
    }

}
