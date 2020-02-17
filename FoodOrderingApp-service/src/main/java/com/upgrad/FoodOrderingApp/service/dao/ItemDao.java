package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String RestaurantUuid, String CategoryUuid) {
        try {
            return null; // TODO: Implement this
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            return null;
        }
    }

    public List<ItemEntity> getItemsByPopularity(String RestaurantUuid) {
        try {
            return null; //TODO: implement this
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            return null;
        }
    }
    public ItemEntity getItemsByUuid(String uuid) {
        try {
            return entityManager.createNamedQuery("getItemsByUuid", ItemEntity.class).setParameter("uuod",uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            return null;
        }
    }
}


