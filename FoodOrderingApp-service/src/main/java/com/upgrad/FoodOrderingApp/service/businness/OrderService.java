package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

//    @Autowired
//    private OrderDao orderDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CouponEntity getCouponByCouponId(String couponId) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CouponEntity getCouponByCouponName(String couponId) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrderEntity> getOrdersByCustomers(String customerId) {
        return null;
    }


}