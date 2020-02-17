package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAddressEntity getAddressByUUID(String addressUuid, String customerUuid){
        try {
            return entityManager.createNamedQuery("getCustomerAddressByUUID", CustomerAddressEntity.class).setParameter("addressUuid",addressUuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            return null;
        }
    }

    public AddressEntity saveAddress(AddressEntity addressEntity){
        try {
            entityManager.persist(addressEntity);
        } catch (Exception e) {
            System.out.println(".....................Database Error");
        }
        return addressEntity;

    }

}