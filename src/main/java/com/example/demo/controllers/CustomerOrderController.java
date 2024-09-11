package com.example.demo.controllers;

import com.example.demo.entities.CustomerOrder;
import com.example.demo.entities.OrderItem;
import com.example.demo.services.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public CustomerOrder createOrder(@RequestBody CustomerOrder order) {
        return customerOrderService.createOrder(order.getCustomerEmail(), order.getCustomerAddress(), order.getItems());
    }

    @PostMapping("/{orderId}/items")
    public void addOrderItem(@PathVariable Long orderId, @RequestBody OrderItem item) {
        customerOrderService.addOrderItem(orderId, item);
    }

    @DeleteMapping("/{orderId}/items")
    public void removeOrderItem(@PathVariable Long orderId, @RequestBody OrderItem item) {
        customerOrderService.removeOrderItem(orderId, item);
    }

    @GetMapping("/{orderId}/total")
    public BigDecimal calculateTotal(@PathVariable Long orderId) {
        return customerOrderService.calculateTotal(orderId);
    }

    @PostMapping("/{orderId}/send-for-delivery")
    public void sendForDelivery(@PathVariable Long orderId) {
        customerOrderService.sendForDelivery(orderId);
    }

    @PatchMapping("/{orderId}/status")
    public void updateDeliveryStatus(@PathVariable Long orderId, @RequestParam String status) {
        customerOrderService.updateDeliveryStatus(orderId, status);
    }

    @GetMapping("/{orderId}")
    public CustomerOrder getOrder(@PathVariable Long orderId) {
        return customerOrderService.getOrder(orderId);
    }
}
