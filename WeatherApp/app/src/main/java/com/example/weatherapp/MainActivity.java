package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText locationInput;
    Button searchButton, forecastButton, gpsButton;
    TextView weatherDisplay;
    FusedLocationProviderClient fusedLocationProviderClient;
    String API_KEY = "5bf47f39cb98f6ddfc654a333dd9d149"; // Replace with your OpenWeatherMap API key
    String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInput = findViewById(R.id.locationInput);
        searchButton = findViewById(R.id.searchButton);
        forecastButton = findViewById(R.id.forecastButton);
        gpsButton = findViewById(R.id.gpsButton);
        weatherDisplay = findViewById(R.id.weatherDisplay);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Search weather by location input
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationInput.getText().toString();
                if (location.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = API_URL + "?q=" + location + "&appid=" + API_KEY;
                new GetWeatherTask().execute(url);
            }
        });

        // Fetch 5-day forecast
        forecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = locationInput.getText().toString();
                if (location.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a location for forecast", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });

        // Fetch weather using GPS
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherUsingGPS();
            }
        });
        Button infoButton = findViewById(R.id.Info); // Ensure the ID matches your button's ID
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getWeatherUsingGPS() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null) {
                                String url = API_URL + "?lat=" + location.getLatitude()
                                        + "&lon=" + location.getLongitude() + "&appid=" + API_KEY;
                                new GetWeatherTask().execute(url);
                            } else {
                                Toast.makeText(MainActivity.this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    class GetWeatherTask extends AsyncTask<String, Void, String> {
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
                    JSONObject main = jsonObject.getJSONObject("main");

                    double tempKelvin = main.getDouble("temp");
                    double feelsLikeKelvin = main.getDouble("feels_like");
                    double tempMaxKelvin = main.getDouble("temp_max");
                    double tempMinKelvin = main.getDouble("temp_min");

                    double tempFahrenheit = (tempKelvin - 273.15) * 9/5 + 32;
                    double feelsLikeFahrenheit = (feelsLikeKelvin - 273.15) * 9/5 + 32;
                    double tempMaxFahrenheit = (tempMaxKelvin - 273.15) * 9/5 + 32;
                    double tempMinFahrenheit = (tempMinKelvin - 273.15) * 9/5 + 32;

                    int pressure = main.getInt("pressure");
                    int humidity = main.getInt("humidity");

                    String weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                    String weatherInfo = "Temperature : " + String.format("%.2f", tempFahrenheit) + " °F\n" +
                            "Condition: " + weatherCondition + "\n" +
                            "Feels Like : " + String.format("%.2f", feelsLikeFahrenheit) + " °F\n" +
                            "Temperature Max : " + String.format("%.2f", tempMaxFahrenheit) + " °F\n" +
                            "Temperature Min : " + String.format("%.2f", tempMinFahrenheit) + " °F\n" +
                            "Pressure : " + pressure + " inHg\n" +
                            "Humidity : " + humidity + "%";

                    weatherDisplay.setText(weatherInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    weatherDisplay.setText("Error parsing weather data.");
                }
            } else {
                weatherDisplay.setText("Error fetching weather data.");
            }
        }
    }
}
