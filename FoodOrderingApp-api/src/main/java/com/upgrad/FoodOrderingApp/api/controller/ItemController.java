package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList.ItemTypeEnum;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    private RestaurantService restaurantService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/item/restaurant/{restaurant_id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getItemList(
            @PathVariable("restaurant_id") final String restaurantId)
            throws RestaurantNotFoundException {

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(restaurantId);

        //TODO: Need to get Item list for restaurent

        List<RestaurantItemEntity> listRestaurantItemEntity = restaurantEntity.getRestaurantItem();

        ArrayList<ItemList> listItemList = new ArrayList<>();

        for(RestaurantItemEntity restaurantItemEntity: listRestaurantItemEntity){
           ItemEntity itemEntity = restaurantItemEntity.getItem();
            listItemList.add(new ItemList().id(UUID.fromString(itemEntity.getUuid()))
                    .price(itemEntity.getPrice()).itemName(itemEntity.getItemName())
                    .itemType(ItemTypeEnum.fromValue(itemEntity.getType().toString())));
        }

        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.addAll(0, listItemList);
        return new ResponseEntity<ItemListResponse>(itemListResponse, HttpStatus.OK);
    }

}