package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestaurantService {

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity restaurantByUUID(String uuid) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> restaurantsByName(String restaurantName) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> restaurantByCategory(String categoryId) {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RestaurantEntity> restaurantsByRating() {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity, Double rating) {
        return null;
    }

}