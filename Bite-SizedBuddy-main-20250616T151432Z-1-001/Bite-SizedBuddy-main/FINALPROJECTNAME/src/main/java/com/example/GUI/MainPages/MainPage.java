package com.example.GUI.MainPages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.GUI.OpeningPage;
import com.example.PetLogic.PetHealth;

public class MainPage {
    //Updateable variables for all the nutrients that are displayed on the main page
    private JLabel carbsLabel;
    private JLabel proteinLabel;
    private JLabel fatsLabel;
    private JLabel sugarsLabel;
    
    private double totalCarbs = 0;
    private double totalProtein = 0;
    private double totalFats = 0;
    private double totalSugars = 0;

    private JLabel hearts; //the image needs to be updated so it has its own variable 
    JLabel healthNum; //the numerical value of health also needs to be updated
    private PetHealth pH;

    JLabel petImage;

    public MainPage(String pet) {
        //Get rid of old page
        OpeningPage o = new OpeningPage();
        o.disposeFrame();

        pH = new PetHealth();

        //Make frame
        JFrame frame = new JFrame("Main Page");
        frame.setSize(550, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Make components for frame

        //Make top section which will have pet name, numerical value of health, and image of hearts
        //Learned to use BorderLayout here: https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
        JPanel topBar = new JPanel(new BorderLayout());
        JLabel petNameLabel = new JLabel(pet); //Set the name of the pet
        petNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        petNameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topBar.add(petNameLabel, BorderLayout.LINE_START);

        //Numerical value for health
        healthNum = new JLabel(Integer.toString(pH.getPetHealth()));
        healthNum.setFont(new Font("Arial", Font.BOLD, 24));
        healthNum.setBorder(BorderFactory.createEmptyBorder(10, 150, 10, 10));
        topBar.add(healthNum, BorderLayout.CENTER);

        //Image for hearts
        //Learned to use FlowLayout here: https://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html
        JPanel healthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hearts = new JLabel(new ImageIcon("Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\HealthBar\\fullHealth.png"));
        healthPanel.add(hearts);
        topBar.add(hearts, BorderLayout.LINE_END);

        //Center section for the image of the pet and the section that displays the nutrients
        //Learned to use GridLayout here: https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));

        //Part that holds the gif of the pet
        JPanel petImagePanel = new JPanel();
        petImagePanel.setPreferredSize(new Dimension(500, 200));
        petImagePanel.setLayout(new GridBagLayout()); // Center content

        //Load the animated GIF
        String gifPath = "Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\PetImages\\HappyDog.gif";
        ImageIcon gifIcon = new ImageIcon(gifPath);

        //Create a scaled JLabel inside a fixed panel using HTML scaling to resize the gif so it fits
        petImage = new JLabel("<html><img src='file:" + gifPath + "' width='500' height='400'></html>");
        petImagePanel.add(petImage);




        //Section where nutrients are displayed
        JPanel nutrientPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        nutrientPanel.setBorder(BorderFactory.createTitledBorder("Today's Nutrients"));

        carbsLabel = new JLabel("kcal: 0");
        proteinLabel = new JLabel("Protein: 0g");
        fatsLabel = new JLabel("Fats: 0g");
        sugarsLabel = new JLabel("Sugars: 0g");

        nutrientPanel.add(carbsLabel);
        nutrientPanel.add(proteinLabel);
        nutrientPanel.add(fatsLabel);
        nutrientPanel.add(sugarsLabel);

        centerPanel.add(petImagePanel); 
        centerPanel.add(nutrientPanel);

        //Button at the bottom that opens the food logging page
        JPanel bottomPanel = new JPanel();
        JButton logFoodButton = new JButton("Log Food");
        logFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Open up a new page but don't close this one
                new FoodLoggingPage(MainPage.this);
            }
        });
        bottomPanel.add(logFoodButton);

        //Add all the components to frame
        frame.add(topBar, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //method to update the nutrient values on the main page every time a food is logged
    public void addNutrients(double carbs, double protein, double fats, double sugars) {
        totalCarbs += carbs;
        totalProtein += protein;
        totalFats += fats;
        totalSugars += sugars;

        //round to two decimals
        totalCarbs = Math.round(totalCarbs * 100.0) / 100.0;;
        totalProtein = Math.round(totalProtein * 100.0) / 100.0;;
        totalFats = Math.round(totalFats * 100.0) / 100.0;;
        totalSugars = Math.round(totalSugars * 100.0) / 100.0;;

        carbsLabel.setText("kcal: " + totalCarbs);
        proteinLabel.setText("Protein: " + totalProtein + "g");
        fatsLabel.setText("Fats: " + totalFats + "g");
        sugarsLabel.setText("Sugars: " + totalSugars + "g");
    }

    public PetHealth getPetHealth() {return pH;}

    //Change the image of hearts depending on the health
    public void updateHearts() {
        //pH.changePetHealth(-20); //use to test if the health bar image updates correctly
        int hp = pH.getPetHealth();
        if (hp>80) {
            hearts.setIcon(new ImageIcon("Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\HealthBar\\fullHealth.png")); 
        } else if (hp>50) {
            hearts.setIcon(new ImageIcon("Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\HealthBar\\twoThirdsHealth.png"));
        } else if (hp>0) {
            hearts.setIcon(new ImageIcon("Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\HealthBar\\oneThirdHealth.png"));
        } else {
            hearts.setIcon(new ImageIcon("Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\HealthBar\\noHealth.png"));
        }
    }

    //Changes the image of the pet depending on how much health it has
    public void updatePetImage() {
        int hp = pH.getPetHealth();
        String gifPath;
        if (hp > 50) {
            gifPath = "Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\PetImages\\HappyDog.gif";
        } else {
            gifPath = "Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\PetImages\\SadDog.gif";
        }
        petImage.setText("<html><img src='file:" + gifPath + "' width='500' height='400'></html>");
    }


    //Update the numerical value of the health that is next to the health bar
    public void updateHealthNum() {
        if (pH.getPetHealth()>100) { //Health can't go above 100
            healthNum.setText("100");
        } else {
            healthNum.setText(Integer.toString(pH.getPetHealth()));
        }
    }

}
