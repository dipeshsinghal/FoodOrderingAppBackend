package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class PaymentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentEntity> getAllPayment(){
        try {
            return entityManager.createNamedQuery("getAllPayment", PaymentEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public PaymentEntity getPaymentByUUID(String uuid){
        try {
            return null;
        } catch (NoResultException nre) {
            return null;
        }
    }

}