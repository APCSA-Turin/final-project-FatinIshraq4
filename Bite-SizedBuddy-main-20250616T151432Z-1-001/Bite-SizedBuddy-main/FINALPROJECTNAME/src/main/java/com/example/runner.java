package com.example;

import com.example.API.API;
import com.example.GUI.OpeningPage;
import com.example.GUI.WelcomePage;
import com.example.GUI.MainPages.FoodLoggingPage;
import com.example.GUI.MainPages.MainPage;

public class runner {

    public static void main(String[] args) {
       new WelcomePage();

       //Test the pages seperately
       //new OpeningPage();
       //new MainPage("Ruffs");
       //new FoodLoggingPage(new MainPage("Ruffs"));
    }

}
