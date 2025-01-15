package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastActivity extends AppCompatActivity {

    TextView forecastDisplay;
    String API_KEY = "5bf47f39cb98f6ddfc654a333dd9d149"; // Replace with your OpenWeatherMap API key
    String API_URL = "https://api.openweathermap.org/data/2.5/forecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);

        forecastDisplay = findViewById(R.id.forecastDisplay);

        String location = getIntent().getStringExtra("location");
        String url = API_URL + "?q=" + location + "&appid=" + API_KEY;
        new GetForecastTask().execute(url);
    }

    class GetForecastTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray list = jsonObject.getJSONArray("list");

                    StringBuilder forecast = new StringBuilder();
                    for (int i = 0; i < list.length(); i += 8) { // Get daily data
                        JSONObject day = list.getJSONObject(i);
                        String date = day.getString("dt_txt");
                        JSONObject main = day.getJSONObject("main");

                        double tempKelvin = main.getDouble("temp");
                        double tempCelsius = (tempKelvin - 273.15) * 9/5 + 32;

                        forecast.append(date).append(": ")
                                .append(String.format("%.2f", tempCelsius)).append("°C\n");
                    }

                    forecastDisplay.setText(forecast.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    forecastDisplay.setText("Error parsing forecast data.");
                }
            } else {
                forecastDisplay.setText("Error fetching forecast data.");
            }
        }
    }
}
