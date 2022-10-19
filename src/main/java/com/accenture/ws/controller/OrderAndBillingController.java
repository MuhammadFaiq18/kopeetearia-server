package com.accenture.ws.controller;

import com.accenture.ws.entity.CafeClerk;
import com.accenture.ws.entity.Order;
import com.accenture.ws.impl.DiscountedBill;
import com.accenture.ws.impl.RegularBill;
import com.accenture.ws.repository.OrderRepository;
import com.accenture.ws.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping
public class OrderAndBillingController {

    @Autowired
    OrderRepository orderRepo;

    CafeClerk clerk;

    @GetMapping("/test")
    public String test() {
        return "bismillah!";
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getOrderList() {
        try {
            List<Order> orderList = orderRepo.findAll();

            if (orderList.isEmpty()) {
                return ResponseHandler.generateResponse("NO_CONTENT", HttpStatus.OK, orderList);
            }

            return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, orderList);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Object> addOrder(@RequestBody Order order) {
        try {
            Order newOrder = new Order();

            newOrder.setOrderName(order.getOrderName());
            newOrder.setPrice((order.getPrice()));
            newOrder.setDiscounted(order.isDiscounted());

            if (order.isDiscounted()) {
                newOrder.setIsDiscountedPercentage(5.0);
            } else {
                newOrder.setIsDiscountedPercentage(0);
            }

            return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, orderRepo.save(newOrder));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PutMapping("orders/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        try {
            Optional<Order> selectedOrder = orderRepo.findById(id);
            Order updatedOrder = selectedOrder.get();

            updatedOrder.setOrderName(order.getOrderName());
            updatedOrder.setPrice(order.getPrice());
            updatedOrder.setDiscounted(order.isDiscounted());

            if (order.isDiscounted()) {
                updatedOrder.setIsDiscountedPercentage(5.0);
            } else {
                updatedOrder.setIsDiscountedPercentage(0);
            }

            return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, orderRepo.save(updatedOrder));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable long id) {
        try {
            Optional<Order> deletedData = orderRepo.findById(id);

            if (deletedData.isPresent()) {
                orderRepo.deleteById(id);
                return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, deletedData);
            } else {
                throw new Exception("NOT_FOUND");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @DeleteMapping("/orders")
    public void deleteAllOrders() {
        orderRepo.deleteAll();
    }

    @GetMapping("/orders/regular")
    public ResponseEntity<Object> getTotalRegularBill() {
        try {
            List<Order> cart = orderRepo.findAll();
            RegularBill regularBill = new RegularBill();

            return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, regularBill.getTotalBill(cart));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/orders/discount")
    public ResponseEntity<Object> getTotalDiscountedBill() {
        try {
            List<Order> cart = orderRepo.findAll();
            DiscountedBill discountedBill = new DiscountedBill();

            return ResponseHandler.generateResponse("SUCCESS", HttpStatus.OK, discountedBill.getTotalBill(cart));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
