package com.example.dz5androidfilesystem;

import androidx.annotation.NonNull;

public class Pizza {

    private String title;

    private double mass;

    private double price;

    private boolean isVegetarian;

    public Pizza() {
        this.title = "";
        this.mass = 0.0;
        this.price = 0.0;
        this.isVegetarian = false;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public String getTitle() {
        return title;
    }

    public double getMass() {
        return mass;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    @NonNull
    @Override
    public String toString(){
        return "Название: "+title+", масса: "+mass+", цена: "+price+", вегетарианская: "+isVegetarian+".";
    }
}
