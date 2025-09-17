package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Moscow",
                "Sydney", "Berlin", "Vienna",
                "Tokyo", "Beijing", "Osaka", "New Delhi"
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = dataList.get(position);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_city, null);
            EditText cityInput = dialogView.findViewById(R.id.editCityName);
            EditText provinceInput = dialogView.findViewById(R.id.editProvinceName);

            // make fields already filled with current city and province
            String[] parts = selectedCity.split(",",2);
            String cityPart = parts.length > 0 ? parts[0].trim() : "";
            String provincePart = parts.length > 1 ? parts[1].trim() : "";

            cityInput.setText(cityPart);
            cityInput.setSelection(cityPart.length());
            provinceInput.setText(provincePart);

            new AlertDialog.Builder(this)
                    .setTitle("Edit city")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog, which) -> {
                        String newCity = cityInput.getText().toString().trim();
                        String newProvince = provinceInput.getText().toString().trim();
                        dataList.set(position, newCity + ", " + newProvince);
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

    }
}