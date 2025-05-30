package com.example.GUI.MainPages;

import com.example.API.API;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FoodLoggingPage {

    private JFrame frame;
    private API api;
    private MainPage mainPage;

    public FoodLoggingPage(MainPage mainPage) {
        api = new API();
        this.mainPage = mainPage;

        //Frame
        frame = new JFrame("Log Food");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Search
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Results List
        //Learned to use BoxLayout here: https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel); //Learned to use a scroll pane here: https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html

        //search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userInput = searchField.getText().trim();
                if (!userInput.isEmpty()) {
                    resultsPanel.removeAll(); // clear old list of results

                    //use the API
                    api.setAPIData(userInput);
                    ArrayList<String> results = api.getInfo();

                    //show the results
                    for (String item : results) {
                        JPanel itemPanel = new JPanel();
                        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                        itemPanel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        ));
                        itemPanel.setBackground(Color.WHITE);

                        //scroll thru text
                        JTextArea resultText = new JTextArea(item);
                        resultText.setLineWrap(true);
                        resultText.setWrapStyleWord(true);
                        resultText.setEditable(false);
                        resultText.setBackground(Color.WHITE);
                        JScrollPane textScroll = new JScrollPane(resultText);
                        textScroll.setPreferredSize(new Dimension(450, 100));

                        //button to add food
                        JButton addButton = new JButton("Add");
                        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                        addButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                //find the nutrient info and save it so that it can be updated on the main page
                                String[] lines = item.split("\n");
                                double protein = 0, carbs = 0, fat = 0, sugar = 0;

                                for (String line : lines) {
                                    if (line.contains("Protein:")) {
                                        protein = parseNutrient(line);
                                    } else if (line.contains("Carbs:") || line.contains("Energy:")) {
                                        carbs = parseNutrient(line);
                                    } else if (line.contains("Fat:")) {
                                        fat = parseNutrient(line);
                                    } else if (line.contains("Sugars:")) {
                                        sugar = parseNutrient(line);
                                    } else if (line.contains("Nutrient Grade:")) {
                                        sendNutrientGrade(line);
                                    }
                                }

                                //Update the health and nutrient values on the main page
                                mainPage.updateHearts();
                                mainPage.updateHealthNum();
                                mainPage.addNutrients(carbs, protein, fat, sugar);
                                JOptionPane.showMessageDialog(frame, "Food added!");
                            }
                        });

                        //setup the itemPanel
                        itemPanel.add(textScroll);
                        itemPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        itemPanel.add(addButton);
                        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
                        resultsPanel.add(itemPanel);
                        resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    }

                    //Refresh
                    resultsPanel.revalidate();
                    resultsPanel.repaint();
                }
            }
        });

        //Close Button
        JPanel bottomPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); //Close the food logging page
            }
        });
        bottomPanel.add(closeButton);

        //setup frame
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    //Method to get the numerical values of the nutrienst 
    //Used chatgpt to make this method
    private double parseNutrient(String line) {
        try {
            String[] parts = line.split(":");
            String num = parts[1].replaceAll("[^\\d.]", "");
            return Double.parseDouble(num);
        } catch (Exception e) {
            return 0;
        }
    }

    //Method to get the nutrition grade so it can be used to update the health
    private void sendNutrientGrade(String line) {
        String grade = "";
        mainPage.getPetHealth().gradeAffectsHealth(grade);
    }
}

