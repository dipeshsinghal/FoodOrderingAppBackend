package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthTokenEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerAuthDao {


    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAuthTokenEntity createAuthToken(final CustomerAuthTokenEntity customerAuthTokenEntity) {
        entityManager.persist(customerAuthTokenEntity);
        return customerAuthTokenEntity;
    }

    public CustomerAuthTokenEntity updateAuthToken(final CustomerAuthTokenEntity customerAuthTokenEntity) {
        entityManager.merge(customerAuthTokenEntity);
        return customerAuthTokenEntity;
    }

    public CustomerAuthTokenEntity getAuthTokenEntityByAccessToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }



}
