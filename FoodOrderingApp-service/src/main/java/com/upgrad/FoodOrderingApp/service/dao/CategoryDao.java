package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CategoryDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CategoryEntity> getCategoryList(){
        try {
            return entityManager.createNamedQuery("getCategoryList", CategoryEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CategoryEntity getCategoryById(String uuid){
        try {
            return entityManager.createNamedQuery("getCategoryById", CategoryEntity.class).setParameter("uuid",uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
