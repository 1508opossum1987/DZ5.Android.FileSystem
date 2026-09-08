package com.example.dz5androidfilesystem;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class XmlHelper {
    private static final String INTERNAL_FILE_NAME = "data.xml";
    private static final String EXTERNAL_FILE_NAME = "pizza_data.xml";
    private static final String CACHE_FILE_NAME = "pizza_cache.xml";
    private static final String TAG_PIZZA = "pizza";
    private static final String TAG_TITLE = "title";
    private static final String TAG_MASS = "mass";
    private static final String TAG_PRICE = "price";
    private static final String TAG_VEGETARIAN = "vegetarian";
    public static boolean exportToXmlInternalStorage(Context context, Pizza pizza) {
        try {
            XmlSerializer serializer = Xml.newSerializer();
            FileOutputStream fos = context.openFileOutput(INTERNAL_FILE_NAME, Context.MODE_PRIVATE);
            serializer.setOutput(fos, "UTF-8");
            serializer.startDocument("UTF-8", true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            serializer.startTag(null, TAG_PIZZA);

            serializer.startTag(null, TAG_TITLE);
            serializer.text(pizza.getTitle());
            serializer.endTag(null, TAG_TITLE);

            serializer.startTag(null, TAG_MASS);
            serializer.text(String.valueOf(pizza.getMass()));
            serializer.endTag(null, TAG_MASS);

            serializer.startTag(null, TAG_PRICE);
            serializer.text(String.valueOf(pizza.getPrice()));
            serializer.endTag(null, TAG_PRICE);

            serializer.startTag(null, TAG_VEGETARIAN);
            serializer.text(String.valueOf(pizza.isVegetarian()));
            serializer.endTag(null, TAG_VEGETARIAN);

            serializer.endTag(null, TAG_PIZZA);
            serializer.endDocument();
            serializer.flush();
            fos.close();

            return true;

        } catch (Exception e) {
            Log.e("Error", "Don't save", e);
            return false;
        }
    }

    public static Pizza importFromXmlInternalStorage(Context context) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            FileInputStream fis = context.openFileInput(INTERNAL_FILE_NAME);
            parser.setInput(new InputStreamReader(fis));

            Pizza pizza = null;
            String currentTag = null;
            String title = "";
            double mass = 0.0;
            double price = 0.0;
            boolean isVegetarian = false;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = tagName;
                        if (tagName.equals(TAG_PIZZA)) {
                            pizza = new Pizza();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (pizza != null && currentTag != null) {
                            String text = parser.getText().trim();
                            if (!text.isEmpty()) {
                                switch (currentTag) {
                                    case TAG_TITLE:
                                        title = text;
                                        break;
                                    case TAG_MASS:
                                        mass = Double.parseDouble(text);
                                        break;
                                    case TAG_PRICE:
                                        price = Double.parseDouble(text);
                                        break;
                                    case TAG_VEGETARIAN:
                                        isVegetarian = Boolean.parseBoolean(text);
                                        break;
                                }
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals(TAG_PIZZA) && pizza != null) {
                            pizza.setTitle(title);
                            pizza.setMass(mass);
                            pizza.setPrice(price);
                            pizza.setVegetarian(isVegetarian);
                            return pizza;
                        }
                        currentTag = null;
                        break;
                }

                eventType = parser.next();
            }

            return pizza;

        } catch (Exception e) {
            Log.e("Error", "Don't read", e);
            return null;
        }
    }

    public static boolean exportToXmlExternalStorage(Context context, Pizza pizza) {
        try {
            File externalDir = context.getExternalFilesDir(null);
            if (externalDir == null) {
                return false;
            }

            File file = new File(externalDir, EXTERNAL_FILE_NAME);

            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                pw.println("<" + TAG_PIZZA + ">");
                pw.println("    <" + TAG_TITLE + ">" + escapeXml(pizza.getTitle()) + "</" + TAG_TITLE + ">");
                pw.println("    <" + TAG_MASS + ">" + pizza.getMass() + "</" + TAG_MASS + ">");
                pw.println("    <" + TAG_PRICE + ">" + pizza.getPrice() + "</" + TAG_PRICE + ">");
                pw.println("    <" + TAG_VEGETARIAN + ">" + pizza.isVegetarian() + "</" + TAG_VEGETARIAN + ">");
                pw.println("</" + TAG_PIZZA + ">");
            }

            return true;

        } catch (Exception e) {
            Log.e("Error", "Don't save", e);
            return false;
        }
    }

    public static Pizza importFromXmlExternalStorage(Context context) {
        try {
            File externalDir = context.getExternalFilesDir(null);
            if (externalDir == null) {
                return null;
            }

            File file = new File(externalDir, EXTERNAL_FILE_NAME);
            if (!file.exists()) {
                Log.e("Error", "File not found");
                return null;
            }

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            FileInputStream fis = new FileInputStream(file);
            parser.setInput(new InputStreamReader(fis));

            Pizza pizza = null;
            String currentTag = null;
            String title = "";
            double mass = 0.0;
            double price = 0.0;
            boolean isVegetarian = false;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = tagName;
                        if (tagName.equals(TAG_PIZZA)) {
                            pizza = new Pizza();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (pizza != null && currentTag != null) {
                            String text = parser.getText().trim();
                            if (!text.isEmpty()) {
                                switch (currentTag) {
                                    case TAG_TITLE:
                                        title = text;
                                        break;
                                    case TAG_MASS:
                                        mass = Double.parseDouble(text);
                                        break;
                                    case TAG_PRICE:
                                        price = Double.parseDouble(text);
                                        break;
                                    case TAG_VEGETARIAN:
                                        isVegetarian = Boolean.parseBoolean(text);
                                        break;
                                }
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals(TAG_PIZZA) && pizza != null) {
                            pizza.setTitle(title);
                            pizza.setMass(mass);
                            pizza.setPrice(price);
                            pizza.setVegetarian(isVegetarian);
                            return pizza;
                        }
                        currentTag = null;
                        break;
                }

                eventType = parser.next();
            }

            return pizza;

        } catch (Exception e) {
            Log.e("Error", "Don't read", e);
            return null;
        }
    }

    private static String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    public static boolean exportToXmlCacheStorage(Context context, Pizza pizza) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                Log.e("Error", "Cache dir not exists");
                return false;
            }

            File file = new File(cacheDir, CACHE_FILE_NAME);

            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                pw.println("<" + TAG_PIZZA + ">");
                pw.println("    <" + TAG_TITLE + ">" + escapeXml(pizza.getTitle()) + "</" + TAG_TITLE + ">");
                pw.println("    <" + TAG_MASS + ">" + pizza.getMass() + "</" + TAG_MASS + ">");
                pw.println("    <" + TAG_PRICE + ">" + pizza.getPrice() + "</" + TAG_PRICE + ">");
                pw.println("    <" + TAG_VEGETARIAN + ">" + pizza.isVegetarian() + "</" + TAG_VEGETARIAN + ">");
                pw.println("</" + TAG_PIZZA + ">");
            }

            Log.d("Cache", "XML saved in cache: " + file.getAbsolutePath());
            return true;

        } catch (Exception e) {
            Log.e("Error", "Don't save", e);
            return false;
        }
    }

    public static Pizza importFromXmlCacheStorage(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                Log.e("Error", "Cache dir not exists");
                return null;
            }

            File file = new File(cacheDir, CACHE_FILE_NAME);
            if (!file.exists()) {
                Log.d("Cache", "Cache file not found: " + file.getAbsolutePath());
                return null;
            }

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            FileInputStream fis = new FileInputStream(file);
            parser.setInput(new InputStreamReader(fis));

            Pizza pizza = null;
            String currentTag = null;
            String title = "";
            double mass = 0.0;
            double price = 0.0;
            boolean isVegetarian = false;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTag = tagName;
                        if (tagName.equals(TAG_PIZZA)) {
                            pizza = new Pizza();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if (pizza != null && currentTag != null) {
                            String text = parser.getText().trim();
                            if (!text.isEmpty()) {
                                switch (currentTag) {
                                    case TAG_TITLE:
                                        title = text;
                                        break;
                                    case TAG_MASS:
                                        mass = Double.parseDouble(text);
                                        break;
                                    case TAG_PRICE:
                                        price = Double.parseDouble(text);
                                        break;
                                    case TAG_VEGETARIAN:
                                        isVegetarian = Boolean.parseBoolean(text);
                                        break;
                                }
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals(TAG_PIZZA) && pizza != null) {
                            pizza.setTitle(title);
                            pizza.setMass(mass);
                            pizza.setPrice(price);
                            pizza.setVegetarian(isVegetarian);
                            Log.d("Cache", "XML is load from cache");
                            return pizza;
                        }
                        currentTag = null;
                        break;
                }

                eventType = parser.next();
            }

            return pizza;

        } catch (Exception e) {
            Log.e("Error", "Don't read", e);
            return null;
        }
    }
}
