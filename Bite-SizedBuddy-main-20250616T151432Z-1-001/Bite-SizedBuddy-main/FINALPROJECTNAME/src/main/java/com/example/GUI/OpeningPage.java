package com.example.GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.example.GUI.MainPages.MainPage;

public class OpeningPage{

    private JFrame frame;

    public OpeningPage() {
        //Get rid of previous page
        WelcomePage w = new WelcomePage();
        w.disposeFrame2();

        // Create the main frame
        frame = new JFrame("Opening Screen");
        frame.setSize(550, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        Font titleLabelFont = new Font("serif", Font.PLAIN, 20);
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Welcome!<br>" +
                               "This app helps you track your meals, but your choices<br>" +  "will affect your virtual pet.<br>" +
                               "Start by giving your virtual dog a name.</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(titleLabelFont);

        JTextField textInput = new JTextField(20); //input text
        textInput.setPreferredSize(new Dimension(110, 110));
        textInput.setMinimumSize(new Dimension(110, 110));
        textInput.setMaximumSize(new Dimension(110, 110));
        textInput.setHorizontalAlignment(JTextField.CENTER);
        Font textInputFont = new Font("serif", Font.PLAIN, 40);
        textInput.setFont(textInputFont);

        Font doneButtonFont = new Font("serif", Font.PLAIN, 40);
        JButton doneButton = new JButton("Done");
        doneButton.setFont(doneButtonFont);
        

        // Layout setup
        JPanel panel = new JPanel(new GridLayout(3, 1)); //create the JPanel object
        panel.add(titleLabel);
        panel.add(textInput);
        panel.add(doneButton);

        //Add Panel to Frame
        frame.add(panel); 

        // Button behavior
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pet = textInput.getText().trim(); 
                if (!pet.isEmpty()) {
                    //Get rid of this page and create the main page with the name the user entered for their pet
                    frame.dispose();
                    new MainPage(pet);
                }
            }
        });

        frame.setVisible(true);
    }

    public void disposeFrame() {frame.dispose();}

}
