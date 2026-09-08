package com.example.dz5androidfilesystem;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class JsonHelper {
    private static final String FILE_NAME = "data.json";
    private static final String EXTERNAL_FILE_NAME = "pizza_data.json";

    static boolean exportToJsonInternalStorage(Context context, Pizza pizza) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(pizza);

        try (
                FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            bos.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            Log.e("Error", "Не удалось сохранить JSON файл", e);
        }

        return false;
    }

    public static Pizza importFromJsonInternalStorage(Context context) {
        try (FileInputStream fis = context.openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            Gson gson = new Gson();
            Pizza pizza = gson.fromJson(br, Pizza.class);
            return pizza;

        } catch (Exception e) {
            Log.e("Error", "Не удалось прочитать JSON файл", e);
            return null;
        }
    }

    public static boolean exportToJsonExternalStorage(Context context, Pizza pizza) {
        try (PrintWriter pw = new PrintWriter(new File(context.getExternalFilesDir(null), EXTERNAL_FILE_NAME))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(pizza);
            pw.println(jsonString);
            return true;
        } catch (Exception e) {
            Log.e("Error", "Не удалось сохранить JSON файл", e);
            return false;
        }
    }

    public static Pizza importFromJsonExternalStorage(Context context) {
        try (
                FileInputStream fis = new FileInputStream(new File(context.getExternalFilesDir(null), EXTERNAL_FILE_NAME));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
        ) {
            Gson gson = new Gson();
            return gson.fromJson(br, Pizza.class);
        } catch (Exception e) {
            Log.e("Error", "Не удалось прочитать JSON файл", e);
            return null;
        }
    }
}