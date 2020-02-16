package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<CategoryEntity> getCategoryList() {
        return categoryDao.getCategoryList();
    }

    public List<CategoryEntity> getCategoriesByRestaurant(String restaurantId) {
        return null;
    }

    public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryId);
        if(categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002","No category by this id");
        }
        return categoryEntity;
    }

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        return null;
    }
}
