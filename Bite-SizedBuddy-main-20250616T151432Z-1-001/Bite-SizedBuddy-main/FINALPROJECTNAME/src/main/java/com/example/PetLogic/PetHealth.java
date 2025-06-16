package com.example.PetLogic;

public class PetHealth {
    
    private int health;

    public PetHealth() {
        health = 100;
    }

    //Method to update health 
    public void changePetHealth(int num) {
        health = health + num;
    }

    //Set and get methods for health
    public int getPetHealth() {return health;}
    public void setPetHealth(int num) {health = num;}

    //When a food is logged this method is called with the nutrition grade as the 
    //parameter and then the health is adjusted accordingly
    public void gradeAffectsHealth(String str) {
        if (str.equals("a")) {
            changePetHealth(25);
        } else if (str.equals("b")) {
            changePetHealth(20);
        } else if (str.equals("c")) {
            changePetHealth(0);
        } else if (str.equals("d")) {
            changePetHealth(-20);
        } else if (str.equals("e")) {
            changePetHealth(-25);
        }
    }
}
