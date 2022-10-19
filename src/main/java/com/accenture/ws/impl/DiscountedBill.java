package com.accenture.ws.impl;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;

import java.util.List;

public class DiscountedBill extends OrderBill {

    public DiscountedBill() {
    }

    public DiscountedBill(CafeClerk clerk) {
        super(clerk);
    }

    @Override
    public double getTotalBill(List<Order> arr) {
        double bill = 0;

        for(int i = 0; i < arr.size(); i++) {
            if(arr.get(i).isDiscounted() == true) {
                double discountedBill = arr.get(i).getPrice() * 0.05;
                bill += arr.get(i).getPrice() - discountedBill;
            } else {
                bill += arr.get(i).getPrice();
            }
        }
        return bill;
    }
}
