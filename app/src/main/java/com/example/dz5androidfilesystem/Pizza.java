package com.example.dz5androidfilesystem;

public class Pizza {
    private String title;
    private double mass;
    private double price;
    private boolean isVegetarian = false;

    Pizza(String title, double mass, double price, boolean isVegetarian){
        this.title = title;
        this.mass = mass;
        this.price = price;
        this.isVegetarian = isVegetarian;
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

    @Override
    public String toString(){
        return "Название: "+title+", масса: "+mass+", цена: "+price+", вегетарианская: "+isVegetarian+".";
    }
}
