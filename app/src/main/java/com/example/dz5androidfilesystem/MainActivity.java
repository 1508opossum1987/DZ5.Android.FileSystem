package com.example.dz5androidfilesystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText = null;
    private EditText massEditText = null;
    private EditText priceEditText = null;
    private CheckBox isVegetarianCheckBox = null;
    private Pizza pizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        massEditText = findViewById(R.id.massEditText);
        priceEditText = findViewById(R.id.priceEditText);
        isVegetarianCheckBox = findViewById(R.id.isVegitarianCheckBox);

        pizza = new Pizza();

        loadFromCacheOnStart();
    }

//    public void saveButtonToInternalStorageOnClick(View view) {
//        String title = titleEditText.getText().toString().trim();
//        String massString = massEditText.getText().toString().trim();
//        String priceString = priceEditText.getText().toString().trim();
//
//        if (title.isEmpty() || massString.isEmpty() || priceString.isEmpty()) {
//            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        try {
//            double mass = Double.parseDouble(massString);
//            double price = Double.parseDouble(priceString);
//            boolean isVegetarian = isVegetarianCheckBox.isChecked();
//
//            pizza.setTitle(title);
//            pizza.setMass(mass);
//            pizza.setPrice(price);
//            pizza.setVegetarian(isVegetarian);
//
//            boolean result = JsonHelper.exportToJsonInternalStorage(this, pizza);
//            if (result) {
//                Toast.makeText(this, "Save To JSON OK", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (NumberFormatException e) {
//            Toast.makeText(this, "Please try correct items", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void loadButtonFromInternalStorageOnClick(View view) {
//        Pizza loadedPizza = JsonHelper.importFromJsonInternalStorage(this);
//
//        if (loadedPizza != null) {
//            pizza = loadedPizza;
//
//            titleEditText.setText(pizza.getTitle());
//            massEditText.setText(String.valueOf(pizza.getMass()));
//            priceEditText.setText(String.valueOf(pizza.getPrice()));
//            isVegetarianCheckBox.setChecked(pizza.isVegetarian());
//
//            Toast.makeText(this, "Load From JSON OK", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void saveButtonToInternalStorageOnClick(View view) {
        if (!collectPizzaData()) {
            return;
        }

        boolean result = XmlHelper.exportToXmlInternalStorage(this, pizza);
        if (result) {
            Toast.makeText(this, "Save To XML OK", Toast.LENGTH_SHORT).show();
            saveToCache();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadButtonFromInternalStorageOnClick(View view) {
        Pizza loadedPizza = XmlHelper.importFromXmlInternalStorage(this);

        if (loadedPizza != null) {
            pizza = loadedPizza;
            updateUI();
            Toast.makeText(this, "Load From XML OK", Toast.LENGTH_SHORT).show();
            saveToCache();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

//    public void saveButtonToExternalStorageOnClick(View view){
//        String title = titleEditText.getText().toString().trim();
//        String massString = massEditText.getText().toString().trim();
//        String priceString = priceEditText.getText().toString().trim();
//
//        if (title.isEmpty() || massString.isEmpty() || priceString.isEmpty()) {
//            Toast.makeText(this, "Please try all places!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        try {
//            double mass = Double.parseDouble(massString);
//            double price = Double.parseDouble(priceString);
//            boolean isVegetarian = isVegetarianCheckBox.isChecked();
//
//            pizza.setTitle(title);
//            pizza.setMass(mass);
//            pizza.setPrice(price);
//            pizza.setVegetarian(isVegetarian);
//
//            boolean result = JsonHelper.exportToJsonExternalStorage(this, pizza);
//            if (result) {
//                Toast.makeText(this, "Save To JSON OK", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (NumberFormatException e) {
//            Toast.makeText(this, "Please try correct items", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void loadButtonFromExternalStorageOnClick(View view){
//        Pizza loadedPizza = JsonHelper.importFromJsonExternalStorage(this);
//
//        if (loadedPizza != null) {
//            pizza = loadedPizza;
//
//            titleEditText.setText(pizza.getTitle());
//            massEditText.setText(String.valueOf(pizza.getMass()));
//            priceEditText.setText(String.valueOf(pizza.getPrice()));
//            isVegetarianCheckBox.setChecked(pizza.isVegetarian());
//
//            Toast.makeText(this, "Load From JSON OK", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void saveButtonToExternalStorageOnClick(View view) {
        if (!collectPizzaData()) {
            return;
        }

        boolean result = XmlHelper.exportToXmlExternalStorage(this, pizza);
        if (result) {
            Toast.makeText(this, "Save To XML OK", Toast.LENGTH_SHORT).show();
            saveToCache();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadButtonFromExternalStorageOnClick(View view) {
        Pizza loadedPizza = XmlHelper.importFromXmlExternalStorage(this);

        if (loadedPizza != null) {
            pizza = loadedPizza;
            updateUI();
            Toast.makeText(this, "Load From XML OK", Toast.LENGTH_SHORT).show();
            saveToCache();
        } else {
            Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromCacheOnStart() {
        Pizza cachedPizza = XmlHelper.importFromXmlCacheStorage(this);

        if (cachedPizza != null) {
            pizza = cachedPizza;

            titleEditText.setText(pizza.getTitle());
            massEditText.setText(String.valueOf(pizza.getMass()));
            priceEditText.setText(String.valueOf(pizza.getPrice()));
            isVegetarianCheckBox.setChecked(pizza.isVegetarian());

            Toast.makeText(this, "Load from cache", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cache is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToCache() {
        boolean result = XmlHelper.exportToXmlCacheStorage(this, pizza);
        if (result) {
            Log.d("MainActivity", "Save to cache");
        } else {
            Log.e("MainActivity", "Don't save to cache");
        }
    }

    private boolean collectPizzaData() {
        String title = titleEditText.getText().toString().trim();
        String massString = massEditText.getText().toString().trim();
        String priceString = priceEditText.getText().toString().trim();

        if (title.isEmpty() || massString.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double mass = Double.parseDouble(massString);
            double price = Double.parseDouble(priceString);
            boolean isVegetarian = isVegetarianCheckBox.isChecked();

            pizza.setTitle(title);
            pizza.setMass(mass);
            pizza.setPrice(price);
            pizza.setVegetarian(isVegetarian);

            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Try correct data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void updateUI() {
        titleEditText.setText(pizza.getTitle());
        massEditText.setText(String.valueOf(pizza.getMass()));
        priceEditText.setText(String.valueOf(pizza.getPrice()));
        isVegetarianCheckBox.setChecked(pizza.isVegetarian());
    }

    public void loadFromCacheOnClick(View view) {
        try {
            Pizza cachedPizza = XmlHelper.importFromXmlCacheStorage(this);

            if (cachedPizza != null) {
                pizza = cachedPizza;

                updateUI();
                Toast.makeText(this, "Load From Cache OK", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cache is empty", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}