package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/category",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryListResponse> getGategoryList() {

        List<CategoryEntity> listCategoryEntity = categoryService.getCategoryList();

        CategoryListResponse categoryListResponse = new CategoryListResponse();
        categoryListResponse.categoryName(listCategoryEntity.toArray().toString());

        return new ResponseEntity<CategoryListResponse>(categoryListResponse, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/category/{category_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryDetails(
            @PathVariable("category_id") final String categoryId)
            throws CategoryNotFoundException {

        if( categoryId == null || categoryId.isEmpty()) {
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);

        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse()
                .id(UUID.fromString(categoryEntity.getUuid()))
                .categoryName(categoryEntity.getCategoryName());

        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse,HttpStatus.OK);
    }
}
