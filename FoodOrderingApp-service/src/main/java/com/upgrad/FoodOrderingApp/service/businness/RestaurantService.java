package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
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

    public List<RestaurantEntity> restaurantsByName(String restaurantName) throws RestaurantNotFoundException {
        if(restaurantName == null || restaurantName.isEmpty()){
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        List<RestaurantEntity> listRestaurantEntity = restaurantDao.restaurantsByName(restaurantName);
        return listRestaurantEntity;
    }

    public List<RestaurantEntity> restaurantByCategory(String categoryId) throws CategoryNotFoundException {
        //TODO: Implement this
        return null;
    }

    public List<RestaurantEntity> restaurantsByRating() {
        return restaurantDao.restaurantsByRating();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity, Double customerRating) throws InvalidRatingException {

        if( customerRating == null || customerRating < 1.0 || customerRating > 5.0 ) {
            throw new InvalidRatingException("IRE-001","Restaurant should be in the range of 1 to 5");
        }

        restaurantEntity.setCustomerRating(customerRating);

        return restaurantDao.updateRestaurant(restaurantEntity);
    }

}
