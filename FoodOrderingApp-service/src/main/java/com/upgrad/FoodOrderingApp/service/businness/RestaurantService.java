package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {


    @Autowired
    private RestaurantDao restaurantDao;

    public RestaurantEntity restaurantByUUID(String uuid) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantDao.restaurantByUUID(uuid);
        if( restaurantEntity == null ) {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return restaurantEntity;
    }

    public List<RestaurantEntity> restaurantsByName(String restaurantName) {
        List<RestaurantEntity> listRestaurantEntity = restaurantDao.restaurantsByName(restaurantName);
        return listRestaurantEntity;
    }

    public List<RestaurantEntity> restaurantByCategory(String categoryId) {
        return null;
    }

    public List<RestaurantEntity> restaurantsByRating() {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity, Double rating) {
        return null;
    }

}
