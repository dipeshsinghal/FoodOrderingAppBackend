package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public CouponEntity getCouponByCouponId(String couponId) throws CouponNotFoundException{
        return orderDao.getCouponByCouponUuid(couponId);
    }

    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {
        CouponEntity couponEntity = orderDao.getCouponByCouponName(couponName);
        if( couponEntity == null ) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }
        return couponEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return null;
    }

    public List<OrderEntity> getOrdersByCustomers(String uuid) {
        return orderDao.getOrdersByCustomers(uuid);
    }


}