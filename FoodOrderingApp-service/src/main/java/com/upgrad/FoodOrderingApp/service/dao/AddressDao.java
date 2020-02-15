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

    public List<AddressEntity> getAllAddressOfCustomer(long customerId){
        try {
            return null;//entityManager.createNamedQuery("getAllAddressOfCustomer", AddressEntity.class).setParameter("customer_id",customerId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerAddressEntity getAddressByUUID(String addressUuid, String customerUuid){
        try {
            return entityManager.createNamedQuery("getCustomerAddressByUUID", CustomerAddressEntity.class).setParameter("addressUuid",addressUuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AddressEntity saveAddress(AddressEntity addressEntity){
        entityManager.persist(addressEntity);
        return addressEntity;
    }

}