package com.example.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class API {

    private ArrayList<String> mainInfo;
    private String lastQuery = "";

    public API() {
        mainInfo = new ArrayList<>();
    }

    public static String getData(String endpoint) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder content;
        try (BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = buff.readLine()) != null) {
                content.append(inputLine);
            }
        }
        connection.disconnect();
        return content.toString();
    }

    public void setAPIData(String query) {
        try {
            if (query.equalsIgnoreCase(lastQuery)) return;

            mainInfo.clear();
            lastQuery = query;

            String endpoint = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=" +
                              query.replace(" ", "+") + "&search_simple=1&action=process&json=1";

            String response = getData(endpoint);
            JSONObject jsonResponse = new JSONObject(response);

            JSONArray products = jsonResponse.getJSONArray("products");

            if (products.length() == 0) {
                mainInfo.add("No products found for \"" + query + "\".");
                return;
            }

            int count = Math.min(products.length(), 6);
            for (int i = 0; i < count; i++) {
                JSONObject product = products.getJSONObject(i);

                String productName = product.optString("product_name", "Unknown Product");
                String brand = product.optString("brands", "Unknown Brand");
                String ingredients = product.optString("ingredients_text", "No ingredient info");
                String nutritionGrade = product.optString("nutrition_grade_fr", "N/A");

                JSONObject nutrients = product.optJSONObject("nutriments");
                String energy = nutrients != null ? nutrients.optString("energy-kcal_100g", "N/A") : "N/A";
                String fat = nutrients != null ? nutrients.optString("fat_100g", "N/A") : "N/A";
                String sugars = nutrients != null ? nutrients.optString("sugars_100g", "N/A") : "N/A";
                String protein = nutrients != null ? nutrients.optString("proteins_100g", "N/A") : "N/A";

                mainInfo.add(
                    "\nProduct " + (i + 1) + ":\n" +
                    "Name: " + productName + "\n" +
                    "Brand: " + brand + "\n" +
                    "Ingredients: " + ingredients + "\n" +
                    "Nutrition Grade: " + nutritionGrade + "\n" +
                    "Per 100g:\n" +
                    "- Energy: " + energy + " kcal\n" +
                    "- Fat: " + fat + " g\n" +
                    "- Sugars: " + sugars + " g\n" +
                    "- Protein: " + protein + " g\n"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mainInfo.add("Error");
        }
    }

    public ArrayList<String> getInfo() {
        return mainInfo;
    }
}
