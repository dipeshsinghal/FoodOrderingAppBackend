package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;

import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/restaurant",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantList() {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/restaurant/name/{restaurant_name}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantListByName(
            @PathVariable("reastaurant_name") final String reastaurantName)
            throws RestaurantNotFoundException {
        return null;
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/restaurant/category/{category_id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantListResponse> getRestaurantListByCategory(final UUID categoryId)
            throws CategoryNotFoundException {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/restaurant/{restaurant_id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantDetails(
            @PathVariable("restaurant_id") final UUID restaurantId)
            throws RestaurantNotFoundException {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/restaurant/{restaurant_id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("restaurant_id") final UUID restaurantId,
            @RequestBody BigDecimal customerRating)
            throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException {

        // Call authenticationService with access token came in authorization field.
        CustomerEntity customerEntity = customerService.getCustomer(authorization);

        return null;
    }
}