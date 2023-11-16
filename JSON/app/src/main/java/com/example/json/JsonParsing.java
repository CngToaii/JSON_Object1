package com.example.json;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Create by Hoang Cong Toai
 */
public class JsonParsing extends AsyncTask<Void, Void, Void>{
    private static final String USG_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    private String bufferdata = "";
    private String finalData= "";
    private String Data = "";

    @Override
    protected Void doInBackground(Void... voids) {

        URL url = null;
        try {
            url = new URL(USG_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line!=null){
                line = bufferedReader.readLine();
                bufferdata = bufferdata+line;

            }
            JSONObject jsonObject = new JSONObject(bufferdata);
            JSONArray jsonArray = jsonObject.getJSONArray("features");
            for(int i = 0;i<jsonArray.length(); i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject properties = jsonObject1.getJSONObject("properties");
                finalData = "Title:" + properties.get("title") + "\n"
                        +"place:" + properties.get("place") + "\n";
                Data = Data + finalData + "\n------------------------\n";



            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        MainActivity.textView.setText(this.Data);
    }
}
