package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String RestaurantUuid, String CategoryUuid) {
        try {
            return new ArrayList<>(); // TODO: Implement this
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            e.printStackTrace();
            return null;
        }
    }

    public List<OrderItemEntity> getItemsByPopularity(String restaurantUuid) {
        try {
            List<OrderItemEntity>  listOrderItemEntity = entityManager.createNamedQuery("getOrderItemsByRestaurant",OrderItemEntity.class).setParameter("uuid",restaurantUuid).getResultList();
            List<OrderItemEntity>  subListOrderItemEntity = new ArrayList<>();
            int listSize = listOrderItemEntity.size();
            if(listSize > 0) {
                subListOrderItemEntity.addAll(listOrderItemEntity.subList(0, Math.min(listSize - 1, 4)));
            }
            return subListOrderItemEntity;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            System.out.println(".....................Database Error");
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }
}


