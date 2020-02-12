package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class OrderController {

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/order/coupon/{coupon_name}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrderCupon(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("coupon_name") final String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrder(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(
            @RequestHeader("authorization") final String authorization,
            SaveOrderRequest saveOrderRequest)
            throws AuthorizationFailedException, CouponNotFoundException,
            AddressNotFoundException, PaymentMethodNotFoundException,
            RestaurantNotFoundException, ItemNotFoundException {
        return null;
    }
}

