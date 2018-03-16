package com.example.xshowroom;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 16-03-2018.
 */

public class Bike {

    public String Model;
    public String Brand;
    public String Color;
    public String Mileage;
    public String Fuel;
    public String ElectricStart;
    public String Engine;
    public String Price;
    public ArrayList<String> Images;

    public Bike(String model,String brand, String color, String mileage, String fuel, String electricStart,
                String engine,String price, ArrayList<String> images)
    {
     Model=model;
     Brand =brand;
     Color=color;
     Mileage=mileage;
     Fuel = fuel;
     ElectricStart=electricStart;
     Engine=engine;
     Price=price;
     Images=images;

    }



}
