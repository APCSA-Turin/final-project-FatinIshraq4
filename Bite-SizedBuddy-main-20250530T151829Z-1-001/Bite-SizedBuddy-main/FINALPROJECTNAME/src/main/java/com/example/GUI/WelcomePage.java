package com.example.GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WelcomePage {

    private JFrame frame;

    public WelcomePage() {
        // Create the main frame
        frame = new JFrame("Opening Screen");
        frame.setSize(550, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components

        //button
        Font nextButtonFont = new Font("serif", Font.PLAIN, 40);
        JButton nextButton = new JButton("Continue to app ➡");
        nextButton.setFont(nextButtonFont);

        //Used this to learn how to use ImageIcon: https://docs.oracle.com/javase/8/docs/api/javax/swing/ImageIcon.html
        //image
        ImageIcon logo = new ImageIcon("C:\\Users\\BT_3N8_02\\Downloads\\Bite-SizedBuddy-main-20250527T151148Z-1-001\\Bite-SizedBuddy-main\\Bite-SizedBuddy-main\\Bite-SizedBuddy-main\\FINALPROJECTNAME\\src\\main\\java\\com\\example\\GUI\\Image.resources\\logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(550, 550,  java.awt.Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        JLabel Jlogo = new JLabel(logo);

        // Layout setup
        JPanel panel = new JPanel(new BorderLayout(4, 4)); //create the JPanel object with BorderLayout
        panel.add(Jlogo, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.SOUTH);

        //adding panel to frame
        frame.add(panel); 

        // Button behavior
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                //Send user to next page and get rid of this page
                frame.dispose();
                new OpeningPage();;
            }
        });

        frame.setVisible(true);
    }

    public void disposeFrame2() {frame.dispose();}

}
