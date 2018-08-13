package com.example.aviasa100.myandroidproject.utils;

import android.widget.ImageView;

/**
 * Created by Aviasa100 on 04/04/2018.
 */
                public class ProductDetails {
                    //private int id;
                    private String name;
                    private double price;
                    private int amount;

                    //constructor

                      public ProductDetails(String name, double price, int amount) {
                        // this.id = id;
                          this.name = name;
                          this.price = price;
                          this.amount = amount;

    }


   // public int getId(){
      //  return id;
    //}
    //public void setId(){
        //this.id = id;
   // }
    public String getName(){

                        return name;
    }
    public  void setName(){
        this.name = name;
    }
    public double getPrice(){

        return price;
    }
    public void setPrice(){
        this.price = price;
    }
    public int getAmount(){

        return amount;
    }
    private void setAmount(){
        this.amount = amount;
    }

}