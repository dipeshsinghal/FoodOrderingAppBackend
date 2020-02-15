package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
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

    public AddressEntity getAddressByUUID(String uuid){
        try {
            return entityManager.createNamedQuery("getAddressByUUID", AddressEntity.class).setParameter("uuid",uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AddressEntity saveAddress(AddressEntity addressEntity){
        entityManager.persist(addressEntity);
        return addressEntity;
    }

}