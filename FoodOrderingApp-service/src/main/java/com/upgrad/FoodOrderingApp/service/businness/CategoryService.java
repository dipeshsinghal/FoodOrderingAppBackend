package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) {
        return null;
    }

    public CategoryEntity getCategoryById(String categoryId) {
        return null;
    }

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return null;
    }
}
