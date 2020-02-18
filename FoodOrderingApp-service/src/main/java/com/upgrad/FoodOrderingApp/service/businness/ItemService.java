package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public ItemEntity getItemsByUuid(String uuid) {
        return itemDao.getItemsByUuid(uuid);

    }

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        List<OrderItemEntity> listOrderItemEntity = itemDao.getItemsByPopularity(restaurantEntity.getUuid());
        List<ItemEntity> listItemEntity = new ArrayList<>();
        for(OrderItemEntity oi: listOrderItemEntity) {
            listItemEntity.add(oi.getItem());
        }
        return listItemEntity;
    }

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        return itemDao.getItemsByCategoryAndRestaurant(restaurantId,categoryId);
    }
}
