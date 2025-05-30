package com.example.PetLogic;

public class Pet {
    
    private String name;
    private PetHealth h = new PetHealth();

    public Pet (String name) {
        this.name=name; 
    }

    //Get method
    public String getName() {return name;}
    
}
