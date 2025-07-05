/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fawry_challenge;

import java.util.ArrayList;

/**
 *
 * @author Roaa
 */
public class ShippingService {
      public static void shipItems(ArrayList<ShippableItems> items) {
        double totalWeight = 0;
        for (ShippableItems item : items) {
            double itemWeight = item.getWeight() * 1000; // grams
            System.out.println(item.getName() + " " + (int)itemWeight + "g");
            totalWeight += item.getWeight();
        }
        System.out.printf("Total package weight: %.1f kg\n", totalWeight);
    }
}
