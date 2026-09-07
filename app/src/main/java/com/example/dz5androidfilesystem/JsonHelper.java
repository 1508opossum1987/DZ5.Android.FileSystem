package com.example.dz5androidfilesystem;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JsonHelper {
    private static final String FILE_NAME = "data.json";

    static boolean exportToJson(Context context, List<Pizza> pizzaList) {
        Context appContext = context.getApplicationContext();

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setPizzaList(pizzaList);
        String jsonString = gson.toJson(dataItems);

        try (
                FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            bos.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            Log.e("FileError", "Не удалось сохранить JSON файл", e);
        }

        return false;
    }

    static List<Pizza> importFromJson(Context context) {
        Context appContext = context.getApplicationContext();

        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr);
        ) {
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(isr, DataItems.class);
            return dataItems.getPizzaList();
        } catch (Exception e) {
            Log.e("FileError", "Не удалось прочитать JSON файл", e);
        }

        return null;
    }
}

class DataItems {
    private List<Pizza> pizzaList;

    List<Pizza> getPizzaList() {
        return pizzaList;
    }

    void setPizzaList(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }
}

