package com.accenture.ws.impl;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;

import java.util.List;


public class RegularBill extends OrderBill {

    public RegularBill() {
    }

    public RegularBill(CafeClerk clerk) {
        super(clerk);
    }

    @Override
    public double getTotalBill(List<Order> arr) {
        double bill = 0;

        for(int i = 0; i < arr.size(); i++) {
            bill += arr.get(i).getPrice();
        }
        return bill;
    }
}
