package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = ("access-token"))
@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategoriesOrderedByName() {

        List<CategoryEntity> listCategoryEntity = categoryService.getAllCategoriesOrderedByName();

        List<CategoryListResponse> listCategoryListResponse = null;

        if (listCategoryEntity.size() != 0) {

            listCategoryListResponse = new ArrayList<>();

            for (CategoryEntity c : listCategoryEntity) {
                listCategoryListResponse.add(new CategoryListResponse()
                        .id(UUID.fromString(c.getUuid()))
                        .categoryName(c.getCategoryName()));
            }
        }

        CategoriesListResponse categoriesListResponse = new CategoriesListResponse().categories(listCategoryListResponse);
        return new ResponseEntity<>(categoriesListResponse, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/category/{category_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryDetails(
            @PathVariable("category_id") final String categoryId)
            throws CategoryNotFoundException {

        if (categoryId == null || categoryId.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

        List<ItemList> listItemList = new ArrayList<ItemList>();
        for (ItemEntity i : categoryEntity.getItems()) {
            listItemList.add(new ItemList()
                    .id(UUID.fromString(i.getUuid()))
                    .itemType(ItemList.ItemTypeEnum.fromValue(i.getType().toString()))
                    .itemName(i.getItemName())
                    .price(i.getPrice()));
        }

        //Disabling this code because Unit test are written differently
//        for(CategoryItemEntity ci : categoryEntity.getCategoryItem()){
//            listItemList.add(new ItemList()
//                    .id(UUID.fromString(ci.getItem().getUuid()))
//                    .itemType(ItemList.ItemTypeEnum.fromValue(ci.getItem().getType().toString()))
//                    .itemName(ci.getItem().getItemName())
//                    .price(ci.getItem().getPrice()));
//        }

        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse()
                .id(UUID.fromString(categoryEntity.getUuid()))
                .categoryName(categoryEntity.getCategoryName())
                .itemList(listItemList);

        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse, HttpStatus.OK);
    }
}
