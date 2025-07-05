/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fawry_challenge;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *
 * @author Roaa
 */
public class Product implements ShippableItems{
    private String name;
    private double price;
    private int quantity;
    private LocalDateTime expiration_date;
    private Boolean shippable;
    private double weight;    

    public Product(String name, double price, int quantity, LocalDateTime expiration_date, Boolean shippable, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiration_date = expiration_date;
        this.shippable = shippable;
        this.weight = weight;
    }

    
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public Boolean getShippable() {
        return shippable;
    }

    public double getWeight() {
        return weight;
    }

    // Setters only for things that could be updated later
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpiration_date(LocalDateTime expirtation_date) {
        this.expiration_date = expirtation_date;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    
    
    
    
}
