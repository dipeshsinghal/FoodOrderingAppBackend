package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private ItemDao itemDao;

    public ItemEntity getItemsByUuid(String uuid) {
        return itemDao.getItemsByUuid(uuid);

    }

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        return itemDao.getItemsByPopularity(restaurantEntity.getUuid());
    }

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String restaurantId, String categoryId) {
        return itemDao.getItemsByCategoryAndRestaurant(restaurantId,categoryId);
    }
}
