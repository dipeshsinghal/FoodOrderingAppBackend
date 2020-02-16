package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.common.Utility;
import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/order/coupon/{coupon_name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCustomerOrderCupon(
            @RequestHeader("authorization") final String authorization,
            @PathVariable("coupon_name") final String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {

        // Call authenticationService with access token came in authorization field.
        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        if(couponName == null || couponName.isEmpty()) {
            throw new CouponNotFoundException("CPF-002","Coupon name field should not be empty");

        }
        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);

        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
                .id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCouponName())
                .percent(couponEntity.getPercent());

        return new ResponseEntity<>(couponDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/order",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrder(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException {

        // Call authenticationService with access token came in authorization field.
        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        List<OrderEntity> listOrderEntity = orderService.getOrdersByCustomers(customerEntity.getUuid());

        List<OrderList> listOrderList = new ArrayList<>();
        for(OrderEntity orderEntity: listOrderEntity){
            listOrderList.add(new OrderList()
                    .id(UUID.fromString(orderEntity.getUuid()))
                    .date(orderEntity.getTimestamp().toString())
                    .bill(BigDecimal.valueOf(orderEntity.getBill()))
                    .discount(BigDecimal.valueOf(orderEntity.getDiscount()))
                    .customer(null)
                    .payment(null)
                    .address(null)
                    .coupon(null)
                    .itemQuantities(new ArrayList<>()));
        }
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse().orders(listOrderList);

        return new ResponseEntity<>(customerOrderResponse,HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(
            @RequestHeader("authorization") final String authorization,
            @RequestBody(required = false) SaveOrderRequest saveOrderRequest)
            throws AuthorizationFailedException, CouponNotFoundException,
            AddressNotFoundException, PaymentMethodNotFoundException,
            RestaurantNotFoundException, ItemNotFoundException {

        // Call authenticationService with access token came in authorization field.
        CustomerEntity customerEntity = customerService.getCustomer(Utility.getTokenFromAuthorizationField(authorization));

        CouponEntity couponEntity = orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString());

        AddressEntity addressEntity = addressService.getAddressByUUID(saveOrderRequest.getAddressId(), customerEntity);

        PaymentEntity paymentEntity = paymentService.getPaymentByUUID(saveOrderRequest.getPaymentId().toString());

        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString());

        List<ItemQuantity> listItemQuantity = saveOrderRequest.getItemQuantities();

        for(ItemQuantity itemQuantity: listItemQuantity ) {
            ItemEntity itemEntity = itemService.getItemsByUuid(itemQuantity.getItemId().toString());
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setCustomer(customerEntity);
        orderEntity.setCoupon(couponEntity);
        orderEntity.setAddress(addressEntity);
        orderEntity.setPayment(paymentEntity);
        orderEntity.setRestaurant(restaurantEntity);
        orderEntity.setBill(saveOrderRequest.getBill().doubleValue());
        orderEntity.setDiscount(saveOrderRequest.getDiscount().doubleValue());

        OrderEntity savedOrderEntity = orderService.saveOrder(orderEntity);

        SaveOrderResponse saveOrderResponse = new SaveOrderResponse().id(savedOrderEntity.getUuid()).status("ORDER SUCCESSFULLY PLACED");

        return new ResponseEntity<>(new SaveOrderResponse(), HttpStatus.OK);
    }
}

