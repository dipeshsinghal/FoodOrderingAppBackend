package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponByCouponUuid(String uuid) {
        try {
            return entityManager.createNamedQuery("getCouponByCouponUuid", CouponEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public CouponEntity getCouponByCouponName(String couponName) {
        try {
            return entityManager.createNamedQuery("getCouponByCouponName", CouponEntity.class).setParameter("couponName", couponName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    public List<OrderEntity> getOrdersByCustomers(String uuid) {
        try {
            return entityManager.createNamedQuery("getOrdersByCustomers", OrderEntity.class).setParameter("uuid", uuid).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}