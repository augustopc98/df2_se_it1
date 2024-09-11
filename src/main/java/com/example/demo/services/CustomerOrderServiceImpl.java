package com.example.demo.services;

import com.example.demo.entities.CustomerOrder;
import com.example.demo.entities.OrderItem;
import com.example.demo.repositories.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public CustomerOrder createOrder(String customerEmail, String customerAddress, List<OrderItem> items) {
        CustomerOrder order = new CustomerOrder(null, customerEmail, customerAddress, new Date(), items);
        return customerOrderRepository.save(order);
    }

    @Override
    public void addOrderItem(Long orderId, OrderItem item) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.addOrderItem(item);
            customerOrderRepository.save(order);
        });
    }

    @Override
    public void removeOrderItem(Long orderId, OrderItem item) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.removeOrderItem(item);
            customerOrderRepository.save(order);
        });
    }

    @Override
    public BigDecimal calculateTotal(Long orderId) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(orderId);
        return orderOptional.map(CustomerOrder::calculateTotal).orElse(BigDecimal.ZERO);
    }

    @Override
    public void sendForDelivery(Long orderId) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.sendForDelivery();
            customerOrderRepository.save(order);
        });
    }

    @Override
    public void updateDeliveryStatus(Long orderId, String status) {
        Optional<CustomerOrder> orderOptional = customerOrderRepository.findById(orderId);
        orderOptional.ifPresent(order -> {
            order.updateDeliveryStatus(status);
            customerOrderRepository.save(order);
        });
    }

    @Override
    public CustomerOrder getOrder(Long orderId) {
        return customerOrderRepository.findById(orderId).orElse(null);
    }
}
