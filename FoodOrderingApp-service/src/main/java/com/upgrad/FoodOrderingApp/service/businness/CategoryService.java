package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) {
        return null;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryEntity getCategoryById(String categoryId) {
        return null;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return null;
    }
}
